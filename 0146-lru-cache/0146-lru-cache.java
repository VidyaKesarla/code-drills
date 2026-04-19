class LRUCache {

    // ── Node: stores key, value, and both pointers ──────────
    // WHY key? When evicting from tail, you only have the node.
    // You need the key to delete it from the HashMap too.
    class Node {
        int key;
        int val;
        Node prev;
        Node next;

        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    private int capacity;
    private Map<Integer, Node> cache;  // key → node
    private Node head;                 // dummy head (most recent side)
    private Node tail;                 // dummy tail (least recent side)

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head = new Node(0, 0);    // dummy, values don't matter
        this.tail = new Node(0, 0);    // dummy, values don't matter
        head.next = tail;
        tail.prev = head;
        // list looks like: HEAD ↔ TAIL (empty)
    }

    // ── helper: remove a node from wherever it is in the list
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // ── helper: insert a node right after HEAD (most recent position)
    private void insertAtFront(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    // ── GET ─────────────────────────────────────────────────
    public int get(int key) {
        if (!cache.containsKey(key)) return -1;

        Node node = cache.get(key);
        // found it — move to front (most recently used)
        remove(node);
        insertAtFront(node);
        return node.val;
    }

    // ── PUT ─────────────────────────────────────────────────
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            // key exists — update value, move to front
            Node node = cache.get(key);
            node.val = value;
            remove(node);
            insertAtFront(node);
        } else {
            // new key — create node, insert at front
            Node node = new Node(key, value);
            cache.put(key, node);
            insertAtFront(node);

            if (cache.size() > capacity) {
                // evict least recently used (node just before TAIL)
                Node lru = tail.prev;
                remove(lru);
                cache.remove(lru.key);
                // ↑ CRITICAL: remove from HashMap too
            }
        }
    }
}

// ============================================================
// DRY RUN
// capacity = 2
// put(1,1), put(2,2), get(1), put(3,3), get(2), put(4,4), get(1), get(3), get(4)
// ============================================================
//
// INITIAL STATE:
// list:  HEAD ↔ TAIL
// cache: {}
//
// ── put(1, 1) ──────────────────────────────────────────────
// key 1 not in cache. Create node(1,1). Insert at front.
// list:  HEAD ↔ [1,1] ↔ TAIL
// cache: {1}
// count=1, capacity=2. No eviction.
//
// ── put(2, 2) ──────────────────────────────────────────────
// key 2 not in cache. Create node(2,2). Insert at front.
// list:  HEAD ↔ [2,2] ↔ [1,1] ↔ TAIL
// cache: {1,2}
// count=2, capacity=2. No eviction.
//
// ── get(1) ─────────────────────────────────────────────────
// key 1 found. Remove node(1,1). Insert at front.
// list:  HEAD ↔ [1,1] ↔ [2,2] ↔ TAIL
// return 1 ✓
//
// ── put(3, 3) ──────────────────────────────────────────────
// key 3 not in cache. Create node(3,3). Insert at front.
// list:  HEAD ↔ [3,3] ↔ [1,1] ↔ [2,2] ↔ TAIL
// count=3 > capacity=2. EVICT.
// LRU = node just before TAIL = node(2,2).
// Remove node(2,2). Remove key 2 from cache.
// list:  HEAD ↔ [3,3] ↔ [1,1] ↔ TAIL
// cache: {1,3}
//
// ── get(2) ─────────────────────────────────────────────────
// key 2 not in cache. return -1 ✓
//
// ── put(4, 4) ──────────────────────────────────────────────
// key 4 not in cache. Create node(4,4). Insert at front.
// list:  HEAD ↔ [4,4] ↔ [3,3] ↔ [1,1] ↔ TAIL
// count=3 > capacity=2. EVICT.
// LRU = node just before TAIL = node(1,1).
// Remove node(1,1). Remove key 1 from cache.
// list:  HEAD ↔ [4,4] ↔ [3,3] ↔ TAIL
// cache: {3,4}
//
// ── get(1) ─────────────────────────────────────────────────
// key 1 not in cache. return -1 ✓
//
// ── get(3) ─────────────────────────────────────────────────
// key 3 found. Remove node(3,3). Insert at front.
// list:  HEAD ↔ [3,3] ↔ [4,4] ↔ TAIL
// return 3 ✓
//
// ── get(4) ─────────────────────────────────────────────────
// key 4 found. Remove node(4,4). Insert at front.
// list:  HEAD ↔ [4,4] ↔ [3,3] ↔ TAIL
// return 4 ✓