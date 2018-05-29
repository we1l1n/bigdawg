package istc.bigdawg.rest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.StringJoiner;

public final class URLUtil {
    private final static String UserAgent = "bigdawg/1";
    private final static String[] percentTable = {
            "%00",
            "%01",
            "%02",
            "%03",
            "%04",
            "%05",
            "%06",
            "%07",
            "%08",
            "%09",
            "%0A",
            "%0B",
            "%0C",
            "%0D",
            "%0E",
            "%0F",
            "%10",
            "%11",
            "%12",
            "%13",
            "%14",
            "%15",
            "%16",
            "%17",
            "%18",
            "%19",
            "%1A",
            "%1B",
            "%1C",
            "%1D",
            "%1E",
            "%1F",
            "%20",
            "%21",
            "%22",
            "%23",
            "%24",
            "%25",
            "%26",
            "%27",
            "%28",
            "%29",
            "%2A",
            "%2B",
            "%2C",
            "-",
            ".",
            "%2F",
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "%3A",
            "%3B",
            "%3C",
            "%3D",
            "%3E",
            "%3F",
            "%40",
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I",
            "J",
            "K",
            "L",
            "M",
            "N",
            "O",
            "P",
            "Q",
            "R",
            "S",
            "T",
            "U",
            "V",
            "W",
            "X",
            "Y",
            "Z",
            "%5B",
            "%5C",
            "%5D",
            "%5E",
            "_",
            "%60",
            "a",
            "b",
            "c",
            "d",
            "e",
            "f",
            "g",
            "h",
            "i",
            "j",
            "k",
            "l",
            "m",
            "n",
            "o",
            "p",
            "q",
            "r",
            "s",
            "t",
            "u",
            "v",
            "w",
            "x",
            "y",
            "z",
            "%7B",
            "%7C",
            "%7D",
            "~",
            "%7F",
            "%80",
            "%81",
            "%82",
            "%83",
            "%84",
            "%85",
            "%86",
            "%87",
            "%88",
            "%89",
            "%8A",
            "%8B",
            "%8C",
            "%8D",
            "%8E",
            "%8F",
            "%90",
            "%91",
            "%92",
            "%93",
            "%94",
            "%95",
            "%96",
            "%97",
            "%98",
            "%99",
            "%9A",
            "%9B",
            "%9C",
            "%9D",
            "%9E",
            "%9F",
            "%A0",
            "%A1",
            "%A2",
            "%A3",
            "%A4",
            "%A5",
            "%A6",
            "%A7",
            "%A8",
            "%A9",
            "%AA",
            "%AB",
            "%AC",
            "%AD",
            "%AE",
            "%AF",
            "%B0",
            "%B1",
            "%B2",
            "%B3",
            "%B4",
            "%B5",
            "%B6",
            "%B7",
            "%B8",
            "%B9",
            "%BA",
            "%BB",
            "%BC",
            "%BD",
            "%BE",
            "%BF",
            "%C0",
            "%C1",
            "%C2",
            "%C3",
            "%C4",
            "%C5",
            "%C6",
            "%C7",
            "%C8",
            "%C9",
            "%CA",
            "%CB",
            "%CC",
            "%CD",
            "%CE",
            "%CF",
            "%D0",
            "%D1",
            "%D2",
            "%D3",
            "%D4",
            "%D5",
            "%D6",
            "%D7",
            "%D8",
            "%D9",
            "%DA",
            "%DB",
            "%DC",
            "%DD",
            "%DE",
            "%DF",
            "%E0",
            "%E1",
            "%E2",
            "%E3",
            "%E4",
            "%E5",
            "%E6",
            "%E7",
            "%E8",
            "%E9",
            "%EA",
            "%EB",
            "%EC",
            "%ED",
            "%EE",
            "%EF",
            "%F0",
            "%F1",
            "%F2",
            "%F3",
            "%F4",
            "%F5",
            "%F6",
            "%F7",
            "%F8",
            "%F9",
            "%FA",
            "%FB",
            "%FC",
            "%FD",
            "%FE",
            "%FF"
    };

    static String percentEncode(String str) {
        StringBuilder sb = new StringBuilder();
        for (int $i = 0, len = str.length(); $i < len; $i++) {
            sb.append(URLUtil.percentTable[(int)str.charAt($i)]);
        }
        return sb.toString();
    }

    static String fetch(String urlStr, HttpMethod method, Map<String, String> headers, String postData, int connectTimeout, int readTimeout) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(connectTimeout);
        urlConnection.setReadTimeout(readTimeout);
        urlConnection.setUseCaches(false);
        urlConnection.setRequestMethod(method.name());
        urlConnection.setRequestProperty("User-Agent", UserAgent);
        for(String header: headers.keySet()) {
            urlConnection.setRequestProperty(header, headers.get(header));
        }
        urlConnection.setDoInput(true);
        InputStream inputStream = urlConnection.getInputStream();
        if (postData != null && method == HttpMethod.POST) {
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Length", String.valueOf(postData.length()));
            OutputStream os = urlConnection.getOutputStream();
            DataOutputStream dataOutputStream= new DataOutputStream(new BufferedOutputStream(os));
            dataOutputStream.writeBytes(postData);
            dataOutputStream.close();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder responseBuilder = new StringBuilder();
        char[] buffer = new char[4096];
        while (bufferedReader.read(buffer, 0,4096) == 4096) {
            responseBuilder.append(buffer);
        }
        responseBuilder.append(buffer);
        return responseBuilder.toString();
    }

    public static String encodeParameters(Map<String, String> parameters) {
        StringJoiner joiner = new StringJoiner("&");
        parameters.forEach((k, v) -> {
            joiner.add(URLUtil.percentEncode(k) + "=" + URLUtil.percentEncode(v));
        });
        return parameters.toString();
    }

    static String appendQueryParameters(String url, Map<String, String> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return url;
        }

        return appendQueryParameters(url, encodeParameters(parameters));
    }

    static String appendQueryParameters(String url, String queryParams) {
        if (queryParams == null) {
            return url;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (!url.contains("?")) {
            sb.append("?");
        }
        else {
            sb.append("&");
        }
        sb.append(queryParams);
        return sb.toString();
    }

}
