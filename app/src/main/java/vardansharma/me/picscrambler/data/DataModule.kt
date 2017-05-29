package vardansharma.me.picscrambler.data

import android.content.Context
import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
class DataModule {
    private val BASE_URL = "https://api.flickr.com/services/rest/"

    @Singleton @Provides fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Singleton @Provides fun provideOkhttp(): OkHttpClient = OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build()

    @Singleton @Provides fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit
            = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// execution mechanism
            .addConverterFactory(MoshiConverterFactory.create(moshi))// how the objects will be converted
            .client(client)// will make the underlying calls
            .baseUrl(BASE_URL)// base url for our requests
            .build()

    @Singleton @Provides fun provideOkHttp3Downloader(client: OkHttpClient) = OkHttp3Downloader(client)

    @Singleton @Provides fun providePicasso(downloader: OkHttp3Downloader, context: Context)
            = Picasso.Builder(context).downloader(downloader)
            .listener { _, _, exception -> run { Log.e("Picasso", exception.message) } }.build()

    @Singleton @Provides fun provideFlickerApi(retrofit: Retrofit) = retrofit.create(FlickerService::class.java)

    @Singleton @Provides fun providePhotoRepository(flickerService: FlickerService) = RemotePhotoRepository(flickerService)
}