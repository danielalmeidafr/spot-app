package com.example.spot.data.remote.network

import com.example.spot.data.remote.dtos.auth.AuthResponse
import com.example.spot.data.remote.dtos.auth.code.ConfirmCodePasswordRequest
import com.example.spot.data.remote.dtos.auth.code.ConfirmCodeSignUpRequest
import com.example.spot.data.remote.dtos.auth.password.ForgotPasswordRequest
import com.example.spot.data.remote.dtos.auth.password.NewPasswordRequest
import com.example.spot.data.remote.dtos.auth.sign.SignInRequest
import com.example.spot.data.remote.dtos.auth.sign.SignOutRequest
import com.example.spot.data.remote.dtos.auth.sign.SignUpRequest
import com.example.spot.data.remote.dtos.create_profile.CreateProfileRequest
import com.example.spot.data.remote.dtos.details.EstablishmentDetailsWrapper
import com.example.spot.data.remote.dtos.favorite.FavoriteEstablishmentWrapper
import com.example.spot.data.remote.dtos.home.establishment.PagedEstablishmentsResponse
import com.example.spot.data.remote.dtos.home.nextschedule.NextScheduleResponse
import com.example.spot.data.remote.dtos.schedules.AppointmentResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotApiService {
    @GET("api/appointments/me/next")
    suspend fun getNextSchedule(): NextScheduleResponse

    @GET("api/establishments")
    suspend fun getAllEstablishments(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sortBy") sortBy: String,
        @Query("sortDir") sortDir: String,
        @Query("name") name: String? = null,
        @Query("customerLatitude") latitude: Double? = null,
        @Query("customerLongitude") longitude: Double? = null
    ): PagedEstablishmentsResponse

    @GET("api/establishments/{establishmentId}")
    suspend fun getEstablishmentDetailsById(
        @Path("establishmentId") establishmentId: String
    ): EstablishmentDetailsWrapper

    @GET("api/appointments/me")
    suspend fun getAllAppointments(
        @Query("month") month: Int
    ): AppointmentResponse

    @POST("api/authentication/sign-in")
    suspend fun signIn(@Body request: SignInRequest): AuthResponse

    @POST("api/authentication/logout")
    suspend fun signOut(@Body request: SignOutRequest)

    @POST("api/authentication/sign-up/request")
    suspend fun signUp(@Body request: SignUpRequest)

    @POST("api/authentication/sign-up/confirm")
    suspend fun confirmCodeSignUp(@Body request: ConfirmCodeSignUpRequest): AuthResponse

    @POST("api/authentication/password-reset/request")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest)

    @POST("api/authentication/password-reset/verify")
    suspend fun confirmCodePassword(@Body request: ConfirmCodePasswordRequest)

    @POST("api/authentication/password-reset/confirm")
    suspend fun newPassword(@Body request: NewPasswordRequest)

    @Multipart
    @POST("api/profiles/me")
    suspend fun createProfile(
        @Part("data") data: RequestBody,
        @Part image: MultipartBody.Part?
    )

    @POST("api/profiles/me/favorites/{establishmentId}")
    suspend fun favorite(
        @Path("establishmentId") establishmentId : String
    )

    @DELETE("api/profiles/me/favorites/{establishmentId}")
    suspend fun unfavorite(
        @Path("establishmentId") establishmentId : String
    )

    @GET("api/profiles/me/favorites")
    suspend fun getAllFavoritesEstablishments(
        @Query("customerLatitude") latitude: Double? = null,
        @Query("customerLongitude") longitude: Double? = null
    ) : FavoriteEstablishmentWrapper
}