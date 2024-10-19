package killhouser.trading.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonConstants {

  public static final String EMPTY_STRING = "";
  public static final String FORWARD_SLASH = "/";
  public static final String UNDERSCORE = "_";
  public static final String SINGLE_PIPE = " | ";

  // TODO : either have env wise bucket or common public bucket.
  public static final String DEFAULT_PROFILE_PICTURE_URL =
      "https://irex-crm-sandbox.s3.ap-south-1.amazonaws.com/profile_picture/default_profile_image.jpg";

  public static final String GOOGLE_CLIENT_FILE_PATH = "client/android/client_secrets.json";

  public static final String DEFAULT_PROFILE_PICTURE_PATH =
      "profile_picture/default_profile_image.jpg";

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Monitoring {
    public static final String X_REQUEST_ID = "x-request-id";
  }
}
