import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
public class Weather {
    private String weatherText;
    private String APIkey = "f7b4d8d54c7eba675248f402ff8b3370";

    public String getUrlContent(String urlAddress) {
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAddress);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            weatherText = "";
        }

        return content.toString();
    }

    public String getWeather(String city) {
        String output = getUrlContent(
                "https://api.openweathermap.org/data/2.5/weather?q="
                        + city
                        + "&appid=" + APIkey + "&units=metric"
        );

        if (!output.isEmpty()) {
            JSONObject object = new JSONObject(output);
            weatherText = "Погода в городе " + city + ":"
                    + "\n\nТемпература: " + object.getJSONObject("main").getDouble("temp") + " °C"
                    + "\nОщущается: " + object.getJSONObject("main").getDouble("feels_like") + " °C"
                    + "\nВлажность: " + object.getJSONObject("main").getDouble("humidity") + " °C"
                    + "\nДавление: " + object.getJSONObject("main").getDouble("pressure");
        }else{
            weatherText = "Город таким названием не существует или у меня нет информации про такой город(" + "\nПожалуйста проверьте на корректность название города";
        }
        return weatherText;
    }
}
