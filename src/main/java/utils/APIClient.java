package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * APIClient 유틸리티 클래스.
 * 다양한 외부 API 호출을 쉽게 하기 위한 HTTP 클라이언트 역할을 합니다.
 */
public class APIClient {

    /**
     * 외부 API에서 데이터를 가져오는 메서드.
     * @param apiUrl 호출할 API URL
     * @return API 응답 문자열
     */
    public static String fetch(String apiUrl) {
        try {
            // URL 객체 생성 및 연결 설정
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // 연결 타임아웃 5초
            connection.setReadTimeout(5000);    // 읽기 타임아웃 5초

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            System.out.println("API Response Code: " + responseCode);

            if (responseCode == 200) { // HTTP OK
                // API 응답 읽기
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();
                return response.toString();
            } else {
                System.out.println("API Request Failed: " + connection.getResponseMessage());
                return null;
            }
        } catch (Exception e) {
            System.out.println("Exception during API call: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 외부 API 호출 후 특정 파라미터나 헤더를 추가하거나 로그 추가 등 추가적인 처리가 필요한 경우
     * 사용할 수 있는 메서드를 여기 추가하면 됩니다.
     */
}
