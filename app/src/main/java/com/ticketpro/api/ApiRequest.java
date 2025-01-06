package com.ticketpro.api;

import com.ticketpro.model.ActivityRequest_Rpc;
import com.ticketpro.model.ArrayOfEnforcementZone;
import com.ticketpro.model.ArrayOfPlaveSearch;
import com.ticketpro.model.ArrayOfValidParkingData;
import com.ticketpro.model.BodyResponse;
import com.ticketpro.model.CallInListResponse;
import com.ticketpro.model.ClientInfoResponse;
import com.ticketpro.model.ColorResponse;
import com.ticketpro.model.CommentGroupCommentResponse;
import com.ticketpro.model.CommentGroupResponse;
import com.ticketpro.model.CommentResponse;
import com.ticketpro.model.ContactResponse;
import com.ticketpro.model.CustomerModuleResponse;
import com.ticketpro.model.CustomerResponse;
import com.ticketpro.model.DeviceFeatures;
import com.ticketpro.model.DeviceGroupResponse;
import com.ticketpro.model.DeviceInfoResponse;
import com.ticketpro.model.DirectionResponse;
import com.ticketpro.model.DurationResponse;
import com.ticketpro.model.DutyResponse;
import com.ticketpro.model.EulaAcceptanceRequest_Rpc;
import com.ticketpro.model.EulaAcceptanceResult;
import com.ticketpro.model.EulaResult;
import com.ticketpro.model.EulaReuest_Rpc;
import com.ticketpro.model.FeatureResponse;
import com.ticketpro.model.FirebaseModel;
import com.ticketpro.model.FirebaseResponse;
import com.ticketpro.model.GPSLocationResponse;
import com.ticketpro.model.GeocodeLocation;
import com.ticketpro.model.HotList_Rpc;
import com.ticketpro.model.HotlistResponse;
import com.ticketpro.model.LocationGroupLocationResponse;
import com.ticketpro.model.LocationGroupResponse;
import com.ticketpro.model.LocationResponse;
import com.ticketpro.model.LotwiseApi;
import com.ticketpro.model.LprBodyResponse;
import com.ticketpro.model.MakeAndModelResponse;
import com.ticketpro.model.MessageResponse;
import com.ticketpro.model.MeterReponse;
import com.ticketpro.model.MeterRequest_Rpc;
import com.ticketpro.model.MeterResponse;
import com.ticketpro.model.ModuleResponse;
import com.ticketpro.model.PermitRequest_Rpc;
import com.ticketpro.model.PermitResponse;
import com.ticketpro.model.PrintMacroResponse;
import com.ticketpro.model.PrintTemplateResponse;
import com.ticketpro.model.RepeatOffenderResponse;
import com.ticketpro.model.RepeatOffender_Rpc;
import com.ticketpro.model.RepeatOffenders;
import com.ticketpro.model.RepeatOffendersLive_Rpc;
import com.ticketpro.model.RequestPOJO;
import com.ticketpro.model.RequestPOJODeviceFeatures;
import com.ticketpro.model.SearchVinResponse;
import com.ticketpro.model.SpecialActivitiesLocationResponse;
import com.ticketpro.model.SpecialActivityDispositionResponse;
import com.ticketpro.model.SpecialActivityReport;
import com.ticketpro.model.SpecialActivityReportResponse;
import com.ticketpro.model.SpecialActivityReport_Rpc;
import com.ticketpro.model.SpecialActivityResponse;
import com.ticketpro.model.StateResponse;
import com.ticketpro.model.StreetPrefixResponse;
import com.ticketpro.model.StreetSuffixResponse;
import com.ticketpro.model.TicketComment_Rpc;
import com.ticketpro.model.TicketHistory;
import com.ticketpro.model.TicketHistoryResponse;
import com.ticketpro.model.TicketPicture_Rpc;
import com.ticketpro.model.TicketResponse;
import com.ticketpro.model.TicketViolationResponse;
import com.ticketpro.model.TicketViolation_Rpc;
import com.ticketpro.model.TicketsResponse;
import com.ticketpro.model.TokenBody;
import com.ticketpro.model.TokenResponse;
import com.ticketpro.model.UserResponse;
import com.ticketpro.model.UserSettingResponse;
import com.ticketpro.model.ValidResult;
import com.ticketpro.model.ValidTicketRequest_Rpc;
import com.ticketpro.model.VendorItemResponse;
import com.ticketpro.model.VendorResponse;
import com.ticketpro.model.VendorServiceRegistrationResponse;
import com.ticketpro.model.VendorServiceResponse;
import com.ticketpro.model.VerifyAndSyncTicketsResponse;
import com.ticketpro.model.VersionUpdate;
import com.ticketpro.model.VersionUpdateResponse;
import com.ticketpro.model.ViolationGroupResponse;
import com.ticketpro.model.ViolationGroupViolationResponse;
import com.ticketpro.model.ViolationResponse;
import com.ticketpro.model.VoidTicketReasonResponse;
import com.ticketpro.model.ZoneResponse;
import com.ticketpro.model.chalk_response.ChalkResponse;
import com.ticketpro.model.devicefeature.ResponseResult;
import com.ticketpro.model.gps.GpsResponse;
import com.ticketpro.model.gps.Location_Json_rpc;
import com.ticketpro.parking.dar.model.Assignments;
import com.ticketpro.parking.dar.model.Dar22500DisposionDropDownElement;
import com.ticketpro.parking.dar.model.Dar22500DisposionDropDownElementResponse;
import com.ticketpro.parking.dar.model.DarAdminDropdownResponse;
import com.ticketpro.parking.dar.model.DarAssignmentLocation;
import com.ticketpro.parking.dar.model.DarAssignmentLocationReportResponse;
import com.ticketpro.parking.dar.model.DarAssignmentLocationResponse;
import com.ticketpro.parking.dar.model.DarAssignmentResponse;
import com.ticketpro.parking.dar.model.DarAssignmentreportResponse;
import com.ticketpro.parking.dar.model.DarBreakAndLunchDropDownElementRes;
import com.ticketpro.parking.dar.model.DarChildEquipmentsResponse;
import com.ticketpro.parking.dar.model.DarDisinfectingElementsResponse;
import com.ticketpro.parking.dar.model.DarDutyResponseR;
import com.ticketpro.parking.dar.model.DarEquipmentsResponse;
import com.ticketpro.parking.dar.model.DarFieldContactDropdownResponse;
import com.ticketpro.parking.dar.model.DarInsertMieageRequest;
import com.ticketpro.parking.dar.model.DarLocationActivityTask;
import com.ticketpro.parking.dar.model.DarLocationActivityTaskResponse;
import com.ticketpro.parking.dar.model.DarOffSiteNonInforcementForm;
import com.ticketpro.parking.dar.model.DarOffStreetPatrolDropDownResponse;
import com.ticketpro.parking.dar.model.DarOffsiteDistrictDropdownResponse;
import com.ticketpro.parking.dar.model.DarOffsiteDropdownElementsResponse;
import com.ticketpro.parking.dar.model.DarOffsiteTrainerDropdownResponse;
import com.ticketpro.parking.dar.model.DarPPZDropdownResponse;
import com.ticketpro.parking.dar.model.DarSchoolDropDownElementRes;
import com.ticketpro.parking.dar.model.DarSeniorDutiesElementsResponse;
import com.ticketpro.parking.dar.model.DarServiceRequestDropDownResponse;
import com.ticketpro.parking.dar.model.DarSignCheckResponse;
import com.ticketpro.parking.dar.model.DarTaskAndActionResponse;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.DarTaskResponse;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.DarTowCompnyDwResponse;
import com.ticketpro.parking.dar.model.DarTowReasonDropDownRes;
import com.ticketpro.parking.dar.model.DarVdrElementsResponse;
import com.ticketpro.parking.dar.model.DarVechicleTaskResponse;
import com.ticketpro.parking.dar.model.DarVehicleListResponse;
import com.ticketpro.parking.dar.model.Equipments;
import com.ticketpro.parking.dar.model.SJPDResonse;
import com.ticketpro.parking.dar.model.TaskAndAction;
import com.ticketpro.parking.dar.model.request.AdminJson_rpc;
import com.ticketpro.parking.dar.model.request.AssignmentLocationReport_rpc;
import com.ticketpro.parking.dar.model.request.AssignmentReport_rpc;
import com.ticketpro.parking.dar.model.request.CallSignJson_rpc;
import com.ticketpro.parking.dar.model.request.CommunityJSON_rpc;
import com.ticketpro.parking.dar.model.request.DarDutyRequestJson_rpc;
import com.ticketpro.parking.dar.model.request.EndMileageJson_rpc;
import com.ticketpro.parking.dar.model.request.FieldContactJson_rpc;
import com.ticketpro.parking.dar.model.request.FlayerJSON_rpc;
import com.ticketpro.parking.dar.model.request.LunchBreakJson_rpc;
import com.ticketpro.parking.dar.model.request.MileageJson_rpc;
import com.ticketpro.parking.dar.model.request.OffStreetJson_rpc;
import com.ticketpro.parking.dar.model.request.PPZJson_rpc;
import com.ticketpro.parking.dar.model.request.RideAlongJSON_rpc;
import com.ticketpro.parking.dar.model.request.SJPDJson_rpc;
import com.ticketpro.parking.dar.model.request.SchoolJson_rpc;
import com.ticketpro.parking.dar.model.request.SeniorDutiesJSON_rpc;
import com.ticketpro.parking.dar.model.request.ServiceRequestJson_rpc;
import com.ticketpro.parking.dar.model.request.SignCheckJson_rpc;
import com.ticketpro.parking.dar.model.request.Task22500Jsonrpc;
import com.ticketpro.parking.dar.model.request.TaskReport_rpc;
import com.ticketpro.parking.dar.model.request.TowJson_rpc;
import com.ticketpro.parking.dar.model.request.TrainingJSON_rpc;
import com.ticketpro.parking.dar.model.request.VehMaintenanceJson_rpc;
import com.ticketpro.parking.dar.model.response.DarAuthorityResult;
import com.ticketpro.parking.dar.model.response.EndMileageResponse;
import com.ticketpro.parking.syncbackup.syncmodel.CSVinfo_Json_rpc;
import com.ticketpro.parking.syncbackup.syncmodel.Dbinfo_Json_rpc;
import com.ticketpro.parking.syncbackup.syncmodel.SyncBackup_Json_rpc;
import com.ticketpro.parking.syncbackup.syncmodel.UploadDebugResponse;
import com.ticketpro.parking.syncreport.requestmodel.SyncReport_Json_rpc;
import com.ticketpro.parking.syncreport.responsemodel.SyncReport_Response;
import com.ticketpro.vendors.ParkMobileSpace;
import com.ticketpro.vendors.ParkMobileZoneList;
import com.ticketpro.vendors.passport2_model.Passport2Array;
import com.ticketpro.vendors.passport2_model.TokenRequest;
import com.ticketpro.vendors.sp_plus.SpPlusModel;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by SANJIV on 14-06-2020.
 */
public interface ApiRequest {

    @GET
    Call<List<SpPlusModel>> spPlusPlate(@Url String url,
                                        @Header("x-api-key") String authToken,
                                        @Header("vendor-name") String vendorName);
    @POST("v1/common/getGPSLocation")

    Call<GpsResponse> getGpsLocation(@Body Location_Json_rpc jbj);

    @GET

    @Multipart
    @POST("/uploadfile")
    Observable<ResponseBody> updateProfile(@Part("custId") RequestBody id,
                                           @Part("full_name") RequestBody fullName,
                                           @Part MultipartBody.Part image);

    /**
     * @return
     */

    @POST("/v3/shared/access-tokens")
    Call<com.ticketpro.vendors.passport2_model.TokenResponse> getTokenForPassportParking2(@Body TokenRequest tokenBody);

    @GET
    Call<Passport2Array> getPP2Space(@Url String url);

    @GET
    Call<ParkMobileZoneList> getZoneParkMobile(@Url String url);

    /**
     * @param url
     * @return
     */
    @GET
    Call<ParkMobileSpace> getParkMobileSingleSpace(@Url String url);

    @GET
    Call<ArrayOfEnforcementZone> getZone(@Url String url);

    @POST
    Call<ResponseBody> getPhotoAlpr(@Url String url, @Body String image);

    @GET
    Call<ArrayOfPlaveSearch> _serchPlate(@Url String url);

    @GET
    Call<ArrayOfValidParkingData> _serchPlate2(@Url String url);

    //CALE
    @GET
    Observable<ArrayOfPlaveSearch> _searchPlateCALEWithoutZero(@Url String url);

    @GET
    Observable<ArrayOfPlaveSearch> _searchPlateCALEWithZero(@Url String url);

    @GET
    Call<ArrayOfValidParkingData> _validateParking(@Url String url);

    @GET("/token")
    Call<ResponseBody> getAccessToken();

    @GET
    Call<LotwiseApi> _getDataBylotname(@Url String url);

    @GET("/parkingsummary/{Lotname}")
    Call<LotwiseApi> getDataBylotname(@Header("Authorization") String token, @Path("Lotname") String LotName);

    @GET("parkingsummary/{purchasedate}/{expirydate}")
    Call<LotwiseApi> getdatabydate(@Header("Authorization") String token, @Path("purchasedate") String purchasedate, @Path("expirydate") String expirydate);

    @GET("parkingsummary/{Lotname}/{purchasedate}/{expirydate}")
    Call<LotwiseApi> getdata_by_date_nd_lot(@Header("Authorization") String token, @Path("Lotname") String LotName, @Path("purchasedate") String purchasedate, @Path("expirydate") String expirydate);

    @POST("Tracking/AddDeviceInfo/")
    Call<FirebaseResponse> postDatatoFBDB(@Body FirebaseModel firebaseModel);

    @GET("json")
    Call<GeocodeLocation> getAddressfromLatLng(@Query("latlng") String latlng, @Query("key") String key);

    @POST("Authentication/GetToken/")
    Call<TokenResponse> getToken(@Body TokenBody tokenBody);

//    @POST("service/genericv1")
//    Observable<CustomerResponse> getCustomers(@Body RequestPOJO jbj);

    @POST("v1/common/getCustomers")
    Observable<CustomerResponse> getCustomers(@Body RequestPOJO jbj);

    //    @POST("service/genericv1")
//    Observable<ClientInfoResponse> getClientInfo(@Body RequestPOJO jbj);
    @POST("v1/common/getClientInfo")
    Observable<ClientInfoResponse> getClientInfo(@Body RequestPOJO jbj);


//    @POST("service/genericv1")
//    Observable<DeviceInfoResponse> getDeviceInfo(@Body RequestPOJO jbj);

    @POST("v1/parking/getDeviceInfo")
    Observable<DeviceInfoResponse> getDeviceInfo(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Call<UserResponse> getUsers(@Body RequestPOJO jbj);

    @POST("v1/common/getUsers")
    Call<UserResponse> getUsers(@Body RequestPOJO jbj);

    @POST("v1/common/getUsers")
    Call<UserResponse> getUsers1(@Body RequestPOJO jbj);

    @POST("v1/parking/updateGCMRegistrationId")
    Call<ResponseBody> updateGCMRegistrationID(@Body RequestPOJO jbj);

    //    @POST("service/genericv1")
//    Observable<UserSettingResponse> getUserSettings(@Body RequestPOJO jbj);
    @POST("v1/common/getUserSettings")
    Observable<UserSettingResponse> getUserSettings(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<MessageResponse> getMessages(@Body RequestPOJO jbj);

    @POST("v1/common/getMessages")
    Observable<MessageResponse> getMessages(@Body RequestPOJO jbj);

    //    @POST("service/genericv1")
//    Observable<BodyResponse> getBodies(@Body RequestPOJO jbj);
    @POST("v1/common/getBodies")
    Observable<BodyResponse> getBodies(@Body RequestPOJO jbj);

    //    @POST("service/genericv1")
//    Observable<LprBodyResponse> getLPRBodies(@Body RequestPOJO jbj);
    @POST("v1/common/getLprBodyMap")
    Observable<LprBodyResponse> getLPRBodies(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<ContactResponse> getContacts(@Body RequestPOJO jbj);

    @POST("v1/common/getContacts")
    Observable<ContactResponse> getContacts(@Body RequestPOJO jbj);

    //    @POST("service/genericv1")
//    Observable<ColorResponse> getColors(@Body RequestPOJO jbj);
    @POST("v1/common/getColors")
    Observable<ColorResponse> getColors(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<CommentResponse> getComments(@Body RequestPOJO jbj);

    @POST("v1/common/getComments")
    Observable<CommentResponse> getComments(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<DirectionResponse> getDirections(@Body RequestPOJO jbj);

    @POST("v1/common/getDirections")
    Observable<DirectionResponse> getDirections(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<DurationResponse> getDurations(@Body RequestPOJO jbj);

    @POST("v1/common/getDurations")
    Observable<DurationResponse> getDurations(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<DutyResponse> getDuties(@Body RequestPOJO jbj);

    @POST("v1/common/getDuties")
    Observable<DutyResponse> getDuties(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<FeatureResponse> getFeatures(@Body RequestPOJO jbj);

    @POST("v1/common/getFeatures")
    Observable<FeatureResponse> getFeatures(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<GPSLocationResponse> getGPSLocations(@Body RequestPOJO jbj);

    @POST("v1/common/getGPSLocations")
    Observable<GPSLocationResponse> getGPSLocations(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<SpecialActivitiesLocationResponse> getSpecialActivitiesLocation(@Body RequestPOJO jbj);

    @POST("v1/common/getSpecialActivitiesLocation")
    Observable<SpecialActivitiesLocationResponse> getSpecialActivitiesLocation(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<HotlistResponse> getHotlist(@Body RequestPOJO jbj);

    @POST("v1/common/getHotlist")
    Observable<HotlistResponse> getHotlist(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<LocationResponse> getLocations(@Body RequestPOJO jbj);

    @POST("v1/common/getLocations")
    Observable<LocationResponse> getLocations(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<RepeatOffenderResponse> getRepeatOffenders(@Body RequestPOJO jbj);

    @POST("v1/common/getRepeatOffenders")
    Observable<RepeatOffenderResponse> getRepeatOffenders(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<MakeAndModelResponse> getMakesAndModels(@Body RequestPOJO jbj);

    @POST("v1/common/getModelsAndMakes")
    Observable<MakeAndModelResponse> getMakesAndModels(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<MeterResponse> getMeters(@Body RequestPOJO jbj);

    @POST("v1/common/getMeters")
    Observable<MeterResponse> getMeters(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<PermitResponse> getPermits(@Body RequestPOJO jbj);

    @POST("v1/common/getPermits")
    Observable<PermitResponse> getPermits(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<StateResponse> getStates(@Body RequestPOJO jbj);

    @POST("v1/common/getStates")
    Observable<StateResponse> getStates(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<StreetPrefixResponse> getStreetPrefixes(@Body RequestPOJO jbj);

    @POST("v1/common/getStreetPrefixes")
    Observable<StreetPrefixResponse> getStreetPrefixes(@Body RequestPOJO jbj);

    //    @POST("service/genericv1")
//    Observable<StreetSuffixResponse> getStreetSuffixes(@Body RequestPOJO jbj);
    @POST("v1/common/getStreetSuffixes")
    Observable<StreetSuffixResponse> getStreetSuffixes(@Body RequestPOJO jbj);

    //    @POST("service/genericv1")
//    Observable<ViolationResponse> getViolations(@Body RequestPOJO jbj);
    @POST("v1/common/getViolations")
    Observable<ViolationResponse> getViolations(@Body RequestPOJO jbj);

    //    @POST("service/genericv1")
//    Observable<ZoneResponse> getZones(@Body RequestPOJO jbj);
    @POST("v1/common/getZones")
    Observable<ZoneResponse> getZones(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<DeviceGroupResponse> getDeviceGroup(@Body RequestPOJO jbj);

    @POST("v1/common/getDeviceGroup")
    Observable<DeviceGroupResponse> getDeviceGroup(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<VoidTicketReasonResponse> getVoidTicketReasons(@Body RequestPOJO jbj);

    @POST("v1/common/getVoidTicketReasons")
    Observable<VoidTicketReasonResponse> getVoidTicketReasons(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<SpecialActivityResponse> getSpecialActivities(@Body RequestPOJO jbj);

    @POST("v1/common/getSpecialActivities")
    Observable<SpecialActivityResponse> getSpecialActivities(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<SpecialActivityDispositionResponse> getSpecialActivityDispositions(@Body RequestPOJO jbj);

    @POST("v1/common/getSpecialActivityDispositions")
    Observable<SpecialActivityDispositionResponse> getSpecialActivityDispositions(@Body RequestPOJO jbj);


//    @POST("service/genericv1")
//    Observable<PrintMacroResponse> getPrintMacros(@Body RequestPOJO jbj);

    @POST("v1/common/getPrintMacros")
    Observable<PrintMacroResponse> getPrintMacros(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<PrintTemplateResponse> getPrintTemplates(@Body RequestPOJO jbj);

    @POST("v1/common/getPrintTemplates")
    Observable<PrintTemplateResponse> getPrintTemplates(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<CallInListResponse> getCallInList(@Body RequestPOJO jbj);

    @POST("v1/common/getCallInList")
    Observable<CallInListResponse> getCallInList(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<LocationGroupResponse> getLocationGroups(@Body RequestPOJO jbj);

    @POST("v1/common/getLocationGroups")
    Observable<LocationGroupResponse> getLocationGroups(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<LocationGroupLocationResponse> getLocationGroupLocations(@Body RequestPOJO jbj);

    @POST("v1/common/getLocationGroupLocations")
    Observable<LocationGroupLocationResponse> getLocationGroupLocations(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<CommentGroupResponse> getCommentGroups(@Body RequestPOJO jbj);

    @POST("v1/common/getCommentGroups")
    Observable<CommentGroupResponse> getCommentGroups(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<CommentGroupCommentResponse> getCommentGroupComments(@Body RequestPOJO jbj);

    @POST("v1/common/getCommentGroupComments")
    Observable<CommentGroupCommentResponse> getCommentGroupComments(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<ViolationGroupResponse> getViolationGroups(@Body RequestPOJO jbj);

    @POST("v1/common/getViolationGroups")
    Observable<ViolationGroupResponse> getViolationGroups(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<ViolationGroupViolationResponse> getViolationGroupViolations(@Body RequestPOJO jbj);


    @POST("v1/common/getViolationGroupViolations")
    Observable<ViolationGroupViolationResponse> getViolationGroupViolations(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<VendorResponse> getVendors(@Body RequestPOJO jbj);

    @POST("v1/common/getVendors")
    Observable<VendorResponse> getVendors(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<VendorServiceResponse> getVendorServices(@Body RequestPOJO jbj);

    @POST("v1/common/getVendorServices")
    Observable<VendorServiceResponse> getVendorServices(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<VendorItemResponse> getVendorItems(@Body RequestPOJO jbj);

    @POST("v1/common/getVendorItems")
    Observable<VendorItemResponse> getVendorItems(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<VendorServiceRegistrationResponse> getVendorServiceRegistrations(@Body RequestPOJO jbj);

    @POST("v1/common/getVendorServiceRegistrations")
    Observable<VendorServiceRegistrationResponse> getVendorServiceRegistrations(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<ModuleResponse> getModules(@Body RequestPOJO jbj);

    @POST("v1/common/getModules")
    Observable<ModuleResponse> getModules(@Body RequestPOJO jbj);

//    @POST("service/genericv1")
//    Observable<CustomerModuleResponse> getCustomerModules(@Body RequestPOJO jbj);

    @POST("v1/common/getCustomerModules")
    Observable<CustomerModuleResponse> getCustomerModules(@Body RequestPOJO jbj);

    @POST("v1/parking/updateTickets")
    Call<TicketResponse> syncTickets(@Body RequestPOJO pojo);

    @POST("v1/parking/updateDevices")
    Call<ResponseBody> syncDevices(@Body RequestPOJO pojo);

    @POST("v1/parking/sendEmail")
    Call<ResponseBody> sendEmail(@Body RequestPOJO pojo);

    @POST("v1/parking/updateChalkStatus")
    Call<ResponseBody> updateChalkStatus(@Body RequestPOJO pojo);

    @POST("v1/parking/genericv1")
    Call<ResponseBody> sendErrorLogs(@Body RequestPOJO pojo);

    @POST("v1/parking/lastUpDateRepeatOffenders")
    Call<RepeatOffenderResponse> lastUpDateRepeatOffenders(@Body RequestPOJO pojo);

    @POST("v1/parking/getlastUpDateRepeatOffenders")
    Call<RepeatOffenderResponse> getlastRepeatOffenderService(@Body RequestPOJO pojo);

    @POST("v1/parking/updateChalks")
    Call<ChalkResponse> syncChalks(@Body RequestPOJO requestPOJO);

    @POST("v1/parking/searchPlates")
    Call<TicketsResponse> getPlateInfo(@Body RequestPOJO pojo);

    @POST("v1/parking/searchHotlist")
    Call<HotlistResponse> searchHotlist(@Body RequestPOJO pojo);

    @POST("v1/parking/searchPermitsByPlate")
    Call<PermitResponse> searchAllPermitByPlate(@Body RequestPOJO pojo);

    @POST("v1/parking/getVersionUpdates")
    Call<VersionUpdateResponse> getVersionUpdates(@Body RequestPOJO pojo);

    @POST("v1/parking/verifyAndSyncTickets")
    Call<VerifyAndSyncTicketsResponse> verifyAndSyncTickets(@Body RequestPOJO pojo);

    @POST("v1/parking/saveDeviceFeatures")
    Call<ResponseResult> saveDeviceFeatures1(@Body RequestPOJODeviceFeatures requestPOJO);

    @POST("v1/parking/updateMobileNowLog")
    Call<ResponseBody> updateMobileNowLog(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<TicketResponse> updatedeviceid(@Body RequestPOJO pojo);

    @POST("v1/parking/updateDutyReports")
    Call<ResponseBody> updateDutyReport(@Body RequestPOJO pojo);

    @POST("v1/parking/getEula")
    Call<EulaResult> getEula(@Body EulaReuest_Rpc pojo);

    @POST("v1/parking/eulaAcceptance")
    Call<EulaAcceptanceResult> getEulaAcceptance(@Body EulaAcceptanceRequest_Rpc pojo);

    @POST("v1/parking/getValidTicket")
    Call<ValidResult> getValidTicket(@Body ValidTicketRequest_Rpc pojo);


    // This code is added by mohit 27/02/2023

    @POST("v1/parking/updateRepeatOffendersCount")
    Call<ResponseBody> updateRepeatOffendersCount(@Body RepeatOffender_Rpc pojo);

    @POST("v1/parking/getRepeatOffendersLive")
    Call<RepeatOffenderResponse> getRepeatOffendersLive(@Body RepeatOffendersLive_Rpc pojo);

    @POST("v1/parking/deleteTicketComments")
    Call<ResponseBody> deleteTicketComments(@Body RequestPOJO pojo);

    @POST("v1/parking/deleteTicketPicture")
    Call<ResponseBody> deleteTicketPicture(@Body RequestPOJO pojo);

    @POST("v1/parking/getTicketHistory")
    Call<TicketHistoryResponse> getTickeHistory(@Body RequestPOJO pojo);

    @POST("v1/parking/updateTicketComments")
    Call<ResponseBody> updateTicketComments(@Body TicketComment_Rpc pojo);


    @POST("service/genericv1")
    Call<ResponseBody> GetLicenseDetail(@Body RequestPOJO pojo);

    @POST("v1/parking/newHotlist")
    Call<ResponseBody> newHotlist(@Body HotList_Rpc pojo);

    @POST("v1/parking/updateSpecialActivityReports")
    Call<ResponseBody> updateSpecialActivityReports(@Body ActivityRequest_Rpc pojo);

    @POST("v1/parking/updateSpecialActivityPictures")
    Call<ResponseBody> updateSpecialActivityPictures(@Body ActivityRequest_Rpc pojo);

    @POST("v1/parking/getSpecialActivityReports")
    Call<SpecialActivityReportResponse> getSpecialActivityReports(@Body SpecialActivityReport_Rpc pojo);

    @POST("v1/parking/updateDutyReports")
    Call<ResponseBody> updateDutyReports(@Body ActivityRequest_Rpc pojo);

    @POST("v1/parking/updateHotListReports")
    Call<ResponseBody> updateHotListReports(@Body ActivityRequest_Rpc pojo);

    @POST("v1/parking/searchMeters")
    Call<MeterReponse> searchMeters(@Body MeterRequest_Rpc pojo);

    @POST("v1/parking/searchPermits")
    Call<PermitResponse> searchPermits(@Body PermitRequest_Rpc pojo);

    @POST("v1/parking/searchPermitVins")
    Call<PermitResponse> searchPermitVins(@Body PermitRequest_Rpc pojo);

    @POST("v1/parking/searchVins")
    Call<SearchVinResponse> searchVins(@Body PermitRequest_Rpc pojo);

    @POST("v1/parking/updateTicketPicture")
    Call<ResponseBody> updateTicketPicture(@Body TicketPicture_Rpc pojo);

    @POST("v1/parking/getTicketViolations")
    Call<TicketViolationResponse> getTicketViolations(@Body TicketViolation_Rpc pojo);

    @POST("v1/parking/uploadDebugLog")
    Call<UploadDebugResponse> uploadDebugLog(@Body SyncBackup_Json_rpc rpc);

    @POST("v1/parking/uploadDatabase")
    Call<UploadDebugResponse> uploadDbBackUp(@Body Dbinfo_Json_rpc rpc);

    @POST("v1/parking/uploadCsvInsideDiagnostics")
    Call<UploadDebugResponse> uploadCSVBackUp(@Body CSVinfo_Json_rpc rpc);

    @POST("v1/parking/updateDutyReports")
    Call<DarDutyResponseR> insertDutyReport(@Body DarDutyRequestJson_rpc pojo);// Insert duty report function using parking module

    /*@Multipart
    @POST("/uploadfile")
    Observable<ResponseBody> updateProfile(@Part("custId") RequestBody id,
                                           @Part("full_name") RequestBody fullName,
                                           @Part MultipartBody.Part image);

    *//**
     *
     * @param url
     * @return
     *//*
    @GET
    Call<ParkMobileZoneList> getZoneParkMobile(@Url String url);

    *//**
     *

     * @return
     *//*

    @POST("/v3/shared/access-tokens")
    Call<com.ticketpro.vendors.passport2_model.TokenResponse> getTokenForPassportParking2(@Body TokenRequest tokenBody);


    @GET
    Call<Passport2Array> getPP2Space(@Url String url);

    @GET
    Call<ParkMobileSpace> getParkMobileSingleSpace(@Url String url);

    @GET
    Call<ArrayOfEnforcementZone> getZone(@Url String url);
    @POST
    Call<ResponseBody> getPhotoAlpr(@Url String url, @Body String image);

    @GET
    Call<ArrayOfPlaveSearch> _serchPlate(@Url String url);

    @GET
    Call<ArrayOfValidParkingData> _validateParking(@Url String url);

    @GET("/token")
    Call<ResponseBody> getAccessToken();

    @GET
    Call<LotwiseApi> _getDataBylotname(@Url String url);

    @GET("/parkingsummary/{Lotname}")
    Call<LotwiseApi> getDataBylotname(@Header("Authorization") String token, @Path("Lotname") String LotName);

    @GET("parkingsummary/{purchasedate}/{expirydate}")
    Call<LotwiseApi> getdatabydate(@Header("Authorization") String token, @Path("purchasedate") String purchasedate, @Path("expirydate") String expirydate);

    @GET("parkingsummary/{Lotname}/{purchasedate}/{expirydate}")
    Call<LotwiseApi> getdata_by_date_nd_lot(@Header("Authorization") String token, @Path("Lotname") String LotName, @Path("purchasedate") String purchasedate, @Path("expirydate") String expirydate);

    @POST("Tracking/AddDeviceInfo/")
    Call<FirebaseResponse> postDatatoFBDB(@Body FirebaseModel firebaseModel);

    @GET("json")
    Call<GeocodeLocation> getAddressfromLatLng(@Query("latlng") String latlng, @Query("key") String key);

    @POST("Authentication/GetToken/")
    Call<TokenResponse> getToken(@Body TokenBody tokenBody);

    @POST("service/genericv1")
    Observable<CustomerResponse> getCustomers(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<ClientInfoResponse> getClientInfo(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<DeviceInfoResponse> getDeviceInfo(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Call<UserResponse> getUsers(@Body RequestPOJO jbj);
    @POST("service/genericv1")
    Call<UserResponse> getUsers1(@Body RequestPOJO jbj);
    @POST("service/genericv1")
    Call<ResponseBody>  updateGCMRegistrationID(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<UserSettingResponse> getUserSettings(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<MessageResponse> getMessages(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<BodyResponse> getBodies(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<LprBodyResponse> getLPRBodies(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<ContactResponse> getContacts(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<ColorResponse> getColors(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<CommentResponse> getComments(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<DirectionResponse> getDirections(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<DurationResponse> getDurations(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<DutyResponse> getDuties(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<FeatureResponse> getFeatures(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<GPSLocationResponse> getGPSLocations(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<SpecialActivitiesLocationResponse> getSpecialActivitiesLocation(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<HotlistResponse> getHotlist(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<LocationResponse> getLocations(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<RepeatOffenderResponse> getRepeatOffenders(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<MakeAndModelResponse> getMakesAndModels(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<MeterResponse> getMeters(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<PermitResponse> getPermits(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<StateResponse> getStates(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<StreetPrefixResponse> getStreetPrefixes(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<StreetSuffixResponse> getStreetSuffixes(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<ViolationResponse> getViolations(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<ZoneResponse> getZones(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<DeviceGroupResponse> getDeviceGroup(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<VoidTicketReasonResponse> getVoidTicketReasons(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<SpecialActivityResponse> getSpecialActivities(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<SpecialActivityDispositionResponse> getSpecialActivityDispositions(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<PrintMacroResponse> getPrintMacros(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<PrintTemplateResponse> getPrintTemplates(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<CallInListResponse> getCallInList(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<LocationGroupResponse> getLocationGroups(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<LocationGroupLocationResponse> getLocationGroupLocations(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<CommentGroupResponse> getCommentGroups(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<CommentGroupCommentResponse> getCommentGroupComments(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<ViolationGroupResponse> getViolationGroups(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<ViolationGroupViolationResponse> getViolationGroupViolations(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<VendorResponse> getVendors(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<VendorServiceResponse> getVendorServices(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<VendorItemResponse> getVendorItems(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<VendorServiceRegistrationResponse> getVendorServiceRegistrations(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<ModuleResponse> getModules(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Observable<CustomerModuleResponse> getCustomerModules(@Body RequestPOJO jbj);

    @POST("service/genericv1")
    Call<TicketResponse> syncTickets(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<ResponseBody> syncDevices(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<ResponseBody> sendEmail(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<ResponseBody> updateChalkStatus(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<ResponseBody> sendErrorLogs(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<RepeatOffenderResponse> lastUpDateRepeatOffenders(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<RepeatOffenderResponse> getlastRepeatOffenderService(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<ChalkResponse> syncChalks(@Body RequestPOJO requestPOJO);

    @POST("service/genericv1")
    Call<TicketsResponse> getPlateInfo(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<HotlistResponse> searchHotlist(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<PermitResponse> searchAllPermitByPlate(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<VersionUpdateResponse> getVersionUpdates(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<VerifyAndSyncTicketsResponse> verifyAndSyncTickets(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<ResponseResult> saveDeviceFeatures1(@Body RequestPOJODeviceFeatures requestPOJO);

    @POST("service/genericv1")
    Call<ResponseBody> updateMobileNowLog(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<TicketResponse> updatedeviceid(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<ResponseBody> updateDutyReport(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<EulaResult> getEula(@Body EulaReuest_Rpc pojo);
 @POST("service/genericv1")
    Call<EulaAcceptanceResult> getEulaAcceptance(@Body EulaAcceptanceRequest_Rpc pojo);

    @POST("service/genericv1")
    Call<ValidResult> getValidTicket(@Body ValidTicketRequest_Rpc pojo);

    //CALE
    @GET
    Observable<ArrayOfPlaveSearch> _searchPlateCALEWithoutZero(@Url String url);

    @GET
    Observable<ArrayOfPlaveSearch> _searchPlateCALEWithZero(@Url String url);

    // This code is added by mohit 27/02/2023

    @POST("service/genericv1")
    Call<ResponseBody> updateRepeatOffendersCount(@Body RepeatOffender_Rpc pojo);

    @POST("service/genericv1")
    Call<RepeatOffenderResponse> getRepeatOffendersLive(@Body RepeatOffendersLive_Rpc pojo);

    @POST("service/genericv1")
    Call<ResponseBody> deleteTicketComments(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<ResponseBody> deleteTicketPicture(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<TicketHistoryResponse> getTickeHistory(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<ResponseBody> updateTicketComments(@Body TicketComment_Rpc pojo);


    @POST("service/genericv1")
    Call<ResponseBody> GetLicenseDetail(@Body RequestPOJO pojo);

    @POST("service/genericv1")
    Call<ResponseBody> newHotlist(@Body HotList_Rpc pojo);

    @POST("service/genericv1")
    Call<ResponseBody> updateSpecialActivityReports(@Body ActivityRequest_Rpc pojo);

    @POST("service/genericv1")
    Call<ResponseBody> updateSpecialActivityPictures(@Body ActivityRequest_Rpc pojo);

    @POST("service/genericv1")
    Call<SpecialActivityReportResponse> getSpecialActivityReports(@Body SpecialActivityReport_Rpc pojo);
    @POST("service/genericv1")
    Call<ResponseBody> updateDutyReports(@Body ActivityRequest_Rpc pojo);

    @POST("service/genericv1")
    Call<ResponseBody> updateHotListReports(@Body ActivityRequest_Rpc pojo);

    @POST("service/genericv1")
    Call<MeterReponse> searchMeters(@Body MeterRequest_Rpc pojo);

    @POST("service/genericv1")
    Call<PermitResponse> searchPermits(@Body PermitRequest_Rpc pojo);

    @POST("service/genericv1")
    Call<PermitResponse> searchPermitVins(@Body PermitRequest_Rpc pojo);

    @POST("service/genericv1")
    Call<SearchVinResponse> searchVins(@Body PermitRequest_Rpc pojo);

    @POST("service/genericv1")
    Call<ResponseBody> updateTicketPicture(@Body TicketPicture_Rpc pojo);
    @POST("service/genericv1")
    Call<TicketViolationResponse> getTicketViolations(@Body TicketViolation_Rpc pojo);

    @POST("service/genericv1")
    Call<UploadDebugResponse> uploadDebugLog(@Body SyncBackup_Json_rpc rpc);

    @POST("service/genericv1")
    Call<UploadDebugResponse> uploadDbBackUp(@Body Dbinfo_Json_rpc rpc);

    @POST("service/genericv1")
    Call<UploadDebugResponse> uploadCSVBackUp(@Body CSVinfo_Json_rpc rpc);

    @POST("service")
    Call<DarDutyResponseR> insertDutyReport(@Body DarDutyRequestJson_rpc pojo);// Insert duty report function using parking module

*/
//********************************************E DAR*****************************************************//

    /**
     * Get equipment data from equipment table
     * @param requestPOJO
     * @return
     */
    @POST("v1/edar/getDarEquipments")
    Observable<DarEquipmentsResponse> getDarEquipments(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarChildEquipments")
    Observable<DarChildEquipmentsResponse> getDarChildEquipments(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarAssignments")
    Observable<DarAssignmentResponse> getDarAssignments(@Body RequestPOJO requestPOJO);
    /* @POST("service/edar")
     Observable<DarAssignmentLocationResponse> getDarAssignmentLocation(@Body RequestPOJO requestPOJO);*/ // instead of assignment location we are using Duty
    @POST("v1/edar/getDarLocationActivityTask")
    Observable<DarLocationActivityTaskResponse> getDarLocationActivityTask(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarTaskAndAction")
    Observable<DarTaskAndActionResponse> getDarTaskAndAction(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarVehicleTask")
    Observable<DarVechicleTaskResponse> getDarVehicleTask(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarSeniorDutiesElements")
    Observable<DarSeniorDutiesElementsResponse> getDarSeniorDutiesElements(@Body RequestPOJO requestPOJO);

    @POST("v1/edar/getDarOffsiteDropdownElements")
    Observable<DarOffsiteDropdownElementsResponse> getDarOffsiteDropdownElements(@Body RequestPOJO requestPOJO);

    @POST("v1/edar/getDar22500DispositionDropDownElement")
    Observable<Dar22500DisposionDropDownElementResponse> getDar22500Dropdown(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarVehicleTypes")
    Observable<DarVehicleListResponse> getDarVehicleList(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarDisinfectingElements")
    Observable<DarDisinfectingElementsResponse> getDarDisinfecting(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarVdrElements")
    Observable<DarVdrElementsResponse> getVDR(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarAdminDropdown")
    Observable<DarAdminDropdownResponse> getAdminDropdown(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarSchoolDropDownElement")
    Observable<DarSchoolDropDownElementRes> getSchoolDropdown(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarFieldContactDropdown")
    Observable<DarFieldContactDropdownResponse> getFieldContactDropdown(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarBreakAndLunchDropDownElement")
    Observable<DarBreakAndLunchDropDownElementRes> getBanL(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarSignCheckDropDownElement")
    Observable<DarSignCheckResponse> getSignCheck(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarPPZDropdownElement")
    Observable<DarPPZDropdownResponse> getPPZ(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarServiceRequestDownElement")
    Observable<DarServiceRequestDropDownResponse> getServiceReq(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarOffStreetPatrolDropDownElement")
    Observable<DarOffStreetPatrolDropDownResponse> getOffStreetPatrol(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarOffsiteTrainerDropdownElements")
    Observable<DarOffsiteTrainerDropdownResponse> getTrainerDw(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarOffsiteDistrictDropdownElements")
    Observable<DarOffsiteDistrictDropdownResponse> getDistrictDw(@Body RequestPOJO requestPOJO);
    @POST("v1/edar/getDarTowReasonDropDownElement")
    Observable<DarTowReasonDropDownRes> getTowReasonDw(@Body RequestPOJO requestPOJO);

    @POST("v1/edar/getDarAuthorityDropdown")
    Observable<DarAuthorityResult> getDarAuthorityResult(@Body RequestPOJO requestPOJO);

    @POST("v1/edar/getDarTowCompanyDownElement")
    Observable<DarTowCompnyDwResponse> getTowCompanyDw(@Body RequestPOJO requestPOJO);


    @POST("v1/edar/darOffSiteNonInforcementFormTrafficControlInsert")
    Call<DarTicketResponse> insertDarOffSiteNonInforcementForm(@Body DarOffSiteNonInforcementForm pojo);
    @POST("v1/edar/dar22500Insert")
    Call<DarTicketResponse> insertFrom22500(@Body Task22500Jsonrpc pojo);
    @POST("v1/edar/insertDarSchoolForm")
    Call<DarTicketResponse> insertSchool(@Body SchoolJson_rpc pojo);
    @POST("v1/edar/darFieldContactInsert")
    Call<DarTicketResponse> insertFieldContact(@Body FieldContactJson_rpc pojo);
    @POST("v1/edar/insertMileage")
    Call<DarInsertMieageRequest> insertMileage(@Body MileageJson_rpc pojo);
    @POST("v1/edar/updateMileage")
    Call<EndMileageResponse> insertEndMileage(@Body EndMileageJson_rpc pojo);
    @POST("v1/edar/darSignCheckDetailInsert")
    Call<DarTicketResponse> insertSignCheck(@Body SignCheckJson_rpc pojo);
    @POST("v1/edar/darVehicleMaintenanceDetailInsert")
    Call<DarTicketResponse> insertvehmaintenace(@Body VehMaintenanceJson_rpc pojo);
    @POST("v1/edar/darAdminDetailsInsert")
    Call<DarTicketResponse> insertAdmin(@Body AdminJson_rpc pojo);
    @POST("v1/edar/darPpzDetailInsert")
    Call<DarTicketResponse> insertPPZ(@Body PPZJson_rpc pojo);
    @POST("v1/edar/darOffStreetPatrolDetailInsert")
    Call<DarTicketResponse> insertOffStreetPatrol(@Body OffStreetJson_rpc pojo);
    @POST("v1/edar/dar_breakAndLunch")
    Call<DarTicketResponse> insertBreakLunch(@Body LunchBreakJson_rpc pojo);
    @POST("v1/edar/darServiceRequestInsert")
    Call<DarTicketResponse> insertServiceRequest(@Body ServiceRequestJson_rpc pojo);
    @POST("v1/edar/OffSiteNonInforcementCommunityMeetingFormInsert")
    Call<DarTicketResponse> insertCommunityMeeting(@Body CommunityJSON_rpc pojo);
    @POST("v1/edar/OffSiteNonInforcementFlyerFormInsert")
    Call<DarTicketResponse> insertFlayer(@Body FlayerJSON_rpc pojo);
    @POST("v1/edar/OffSiteNonInforcementRideAlongFormInsert")
    Call<DarTicketResponse> insertRideAlong(@Body RideAlongJSON_rpc pojo);
    @POST("v1/edar/OffSiteNonInforcementTrainingFormInsert")
    Call<DarTicketResponse> insertTraining(@Body TrainingJSON_rpc pojo);
    @POST("v1/edar/OffSiteNonInforcementSeniorDutyFormInsert")
    Call<DarTicketResponse> insertSeniorDuties(@Body SeniorDutiesJSON_rpc pojo);
    @POST("v1/edar/dar_assignment_report")
    Call<DarAssignmentreportResponse> insertAssignmentReport(@Body AssignmentReport_rpc pojo);
    @POST("v1/edar/dar_assignment_location_report")
    Call<DarAssignmentLocationReportResponse> insertAssignmentLocationReport(@Body AssignmentLocationReport_rpc pojo);
    @POST("v1/edar/dar_task_report")
    Call<DarTaskReportResponse> insertTaskReport(@Body TaskReport_rpc pojo);
    /*  @POST("v1/service/edar")
      Call<DarTicketResponse> insertCallSign(@Body CallSignJson_rpc pojo);*/ //Not used till 19-03-2024
    @POST("v1/edar/sjpdFormInsert")
    Call<SJPDResonse> insertSJPD(@Body SJPDJson_rpc pojo);
    @POST("v1/edar/darTowDetailInsert")
    Call<DarTicketResponse> insertTow(@Body TowJson_rpc pojo);

    //Offline call
    @POST("v1/edar/dar22500Insert")
    Observable<DarTicketResponse> insertFrom22500Obs(@Body Task22500Jsonrpc pojo);
    @POST("v1/edar/darFieldContactInsert")
    Observable<DarTicketResponse> insertFieldContactObs(@Body FieldContactJson_rpc pojo);
    @POST("v1/edar/darOffSiteNonInforcementFormTrafficControlInsert")
    Observable<DarTicketResponse> insertTrafficContObs(@Body DarOffSiteNonInforcementForm pojo);
    @POST("v1/edar/insertDarSchoolForm")
    Observable<DarTicketResponse> insertSchoolObs(@Body SchoolJson_rpc pojo);
    @POST("v1/edar/darSignCheckDetailInsert")
    Observable<DarTicketResponse> insertSignCheckObs(@Body SignCheckJson_rpc pojo);
    @POST("v1/edar/darAdminDetailsInsert")
    Observable<DarTicketResponse> insertAdminObs(@Body AdminJson_rpc pojo);
    @POST("v1/edar/darPpzDetailInsert")
    Observable<DarTicketResponse> insertPPZObs(@Body PPZJson_rpc pojo);
    @POST("v1/edar/darOffStreetPatrolDetailInsert")
    Observable<DarTicketResponse> insertOffStreetPatrolObs(@Body OffStreetJson_rpc pojo);
    @POST("v1/edar/dar_breakAndLunch")
    Observable<DarTicketResponse> insertBreakLunchObs(@Body LunchBreakJson_rpc pojo);
    @POST("v1/edar/darServiceRequestInsert")
    Observable<DarTicketResponse> insertServiceRequestObs(@Body ServiceRequestJson_rpc pojo);
    @POST("v1/edar/OffSiteNonInforcementCommunityMeetingFormInsert")
    Observable<DarTicketResponse> insertCommunityMeetingObs(@Body CommunityJSON_rpc pojo);
    @POST("v1/edar/OffSiteNonInforcementFlyerFormInsert")
    Observable<DarTicketResponse> insertFlayerObs(@Body FlayerJSON_rpc pojo);
    @POST("v1/edar/OffSiteNonInforcementRideAlongFormInsert")
    Observable<DarTicketResponse> insertRideAlongObs(@Body RideAlongJSON_rpc pojo);
    @POST("v1/edar/OffSiteNonInforcementTrainingFormInsert")
    Observable<DarTicketResponse> insertTrainingObs(@Body TrainingJSON_rpc pojo);
    @POST("v1/edar/OffSiteNonInforcementSeniorDutyFormInsert")
    Observable<DarTicketResponse> insertSeniorDutiesObs(@Body SeniorDutiesJSON_rpc pojo);

    @POST("v1/edar/darReportRequest")
    Call<SyncReport_Response> syncReport(@Body SyncReport_Json_rpc rpc);

}

