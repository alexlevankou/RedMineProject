package by.alexlevankou.redmineproject;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class ServiceGenerator {

    public static final String API_BASE_URL = "http://192.168.1.17:3001";

    private static RestAdapter.Builder builder = new RestAdapter.Builder()
            .setEndpoint(API_BASE_URL)
            .setClient(new OkClient(new OkHttpClient()));

    public static <S> S createService(Context ctx,Class<S> serviceClass) {
        return createService(ctx,serviceClass, null, null);
    }

    public static <S> S createService(Context ctx, Class<S> serviceClass, String username, String password) {
        if (!username.isEmpty() && !password.isEmpty()) {
            // concatenate username and password with colon for authentication
            String credentials = username + ":" + password;
            // create Base64 encodet string
            final String basic =
                    "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Authorization", basic);
                    request.addHeader("Accept", "application/json");
                }
            });
        }

        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);
    }
}
