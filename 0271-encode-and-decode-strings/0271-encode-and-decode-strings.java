public class Codec {

    // Encodes a list of strings to a single string.
    //here the solution is quite tricky in the sense , any string can contain ASCII character. So we have to use the approach of not using a ASCII character. We should use a delimiter -> special character or sequence of characters that we insert between each string when we combine them into one. this allows us to correctly separate the strings when we decode them. therefore we have to use a unicode character
    public String encode(List<String> strs) {
        StringBuilder encodedString = new StringBuilder();
        for(String s: strs){
            encodedString.append(s);
            encodedString.append("π");
        }
        return encodedString.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        String [] decodedStrings = s.split("π",-1);

        return new ArrayList<>(Arrays.asList(decodedStrings).subList(0, decodedStrings.length - 1));
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(strs));