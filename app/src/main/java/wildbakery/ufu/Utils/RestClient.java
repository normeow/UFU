package wildbakery.ufu.Utils;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

public class RestClient {


    public static <T> T create(String baseUrl, Class<T> apiInterfaceClass, Map<String, String> queries) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient(queries))
                .build();
        return retrofit.create(apiInterfaceClass);
    }

    public static OkHttpClient getOkHttpClient(Map<String, String> queries) {
        return new OkHttpClient.Builder()
                .addInterceptor(makeQueriesInterceptor(queries))
                .build();
    }

    private static Interceptor makeQueriesInterceptor(final Map<String, String> queries) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                HttpUrl url = addQueryParametersToUrl(chain.request().url(), queries);
                Request request = chain.request().newBuilder().url(url).build();
                return chain.proceed(request);
            }
        };
    }

    private static HttpUrl addQueryParametersToUrl(HttpUrl url, Map<String, String> queries) {
        HttpUrl.Builder builder = url.newBuilder();
        for (Entry<String, String> entry : queries.entrySet()) {
            builder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

}