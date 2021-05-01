package com.golnaz.storyteltest.utils.network

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.net.HttpURLConnection

class RetrofitException : RuntimeException() {
    fun httpError(url: String?, response: Response<*>, retrofit: Retrofit?) {
        val message = response.code().toString() + " " + response.message()
        return RetrofitException(message, url, response, Kind.HTTP, null, retrofit)
    }

    fun networkError(exception: IOException) {
        return RetrofitException(exception.message, null, null, Kind.NETWORK, exception, null)
    }

    fun timeoutError(exception: Throwable) {
        return RetrofitException(exception.message, null, null, Kind.TIMEOUT, exception, null)
    }

    fun unexpectedError(exception: Throwable) {
        return RetrofitException(exception.message, null, null, Kind.UNEXPECTED, exception, null)
    }

    /**
     * Identifies the event kind which triggered a [RetrofitException].
     */
    enum class Kind {
        /**
         * An [IOException] occurred while communicating to the server.
         */
        NETWORK,

        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,

        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED, TIMEOUT
    }

    private var url: String? = null
    private var response: Response<*>? = null
    private var kind: Kind? = null
    private var retrofit: Retrofit? = null

    fun RetrofitException(
        message: String?,
        url: String?,
        response: Response<*>?,
        kind: Kind?,
        exception: Throwable?,
        retrofit: Retrofit?
    ) {
        this.url = url
        this.response = response
        this.kind = kind
        this.retrofit = retrofit
    }

    /**
     * The request URL which produced the error.
     */
    fun getUrl(): String? {
        return url
    }

    /**
     * Response object containing status code, headers, body, etc.
     */
    fun getResponse(): Response<*>? {
        return response
    }

    /**
     * The event kind which triggered this error.
     */
    fun getKind(): Kind? {
        return kind
    }

    /**
     * The Retrofit this request was executed on
     */
    fun getRetrofit(): Retrofit? {
        return retrofit
    }

    /**
     * HTTP response body converted to specified `type`. `null` if there is no
     * response.
     *
     * @throws IOException if unable to convert the body to the specified `type`.
     */
    @Throws(IOException::class)
    fun getErrorBodyAs(type: Class<BaseResponse>): BaseResponse {
        if (getKind() == Kind.TIMEOUT || getKind() == Kind.NETWORK) {
            return BaseResponse(500, false, " An error occurred. Please try again")
        }
        if (response == null || response!!.errorBody() == null) {
            return BaseResponse(400, false, message)
        }
        if (response!!.code() == HttpURLConnection.HTTP_UNAUTHORIZED || response!!.code() == HttpURLConnection.HTTP_FORBIDDEN) {
            return BaseResponse(response!!.code(), false, message)
        }
        if (response!!.code() == HttpURLConnection.HTTP_NOT_FOUND) {
            return BaseResponse(response!!.code(), false, message)
        }
        val converter: Converter<ResponseBody?, BaseResponse> =
            retrofit!!.responseBodyConverter<BaseResponse>(type, arrayOfNulls(0))
        return converter.convert(response!!.errorBody())!!
    }
}