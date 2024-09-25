package killhouser.trading.exception;

import lombok.Getter;

@Getter
public enum ResponseCode implements ErrorCode {

  // generic errors
  NOT_FOUND {
    @Override
    public int httpStatusCode() {
      return 404;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.VALIDATION_ERROR;
    }
  },
  LOGIN_ERROR {
    @Override
    public int httpStatusCode() {
      return 400;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.VALIDATION_ERROR;
    }
  },
  TOKEN_GENERATION_ERROR {
    @Override
    public int httpStatusCode() {
      return 500;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.INTERNAL_ERROR;
    }
  },
  TOKEN_EXTRACTION_ERROR {
    @Override
    public int httpStatusCode() {
      return 500;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.INTERNAL_ERROR;
    }
  },
  USER_DELETED {
    @Override
    public int httpStatusCode() {
      return 404;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.VALIDATION_ERROR;
    }
  },
  UNKNOWN_EXCEPTION {
    @Override
    public int httpStatusCode() {
      return 500;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.INTERNAL_ERROR;
    }
  },
  JSON_ERROR {
    @Override
    public int httpStatusCode() {
      return 500;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.INTERNAL_ERROR;
    }
  },

  FORBIDDEN_ERROR {
    @Override
    public int httpStatusCode() {
      return 403;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.AUTHORISATION_ERROR;
    }
  },

  // db errors
  MYSQL_ERROR {
    @Override
    public int httpStatusCode() {
      return 500;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.INTERNAL_ERROR;
    }
  },
  MYSQL_CONSTRAINT_VIOLATION_ERROR {
    @Override
    public int httpStatusCode() {
      return 500;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.INTERNAL_ERROR;
    }
  },
  MONGO_ERROR {
    @Override
    public int httpStatusCode() {
      return 500;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.INTERNAL_ERROR;
    }
  },
  ENTITY_NOT_FOUND {
    @Override
    public int httpStatusCode() {
      return 404;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.VALIDATION_ERROR;
    }
  },

  INVALID_REQUEST_ERROR {
    @Override
    public int httpStatusCode() {
      return 400;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.VALIDATION_ERROR;
    }
  },

  NULL_POINTER_ERROR {
    @Override
    public int httpStatusCode() {
      return 500;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.INTERNAL_ERROR;
    }
  },
  // sqs errors
  SQS_ERROR {

    @Override
    public int httpStatusCode() {
      return 500;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.INTERNAL_ERROR;
    }
  },

  CACHE_ERROR {
    @Override
    public int httpStatusCode() {
      return 500;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.INTERNAL_ERROR;
    }
  },

  S3_ERROR {
    @Override
    public int httpStatusCode() {
      return 500;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.INTERNAL_ERROR;
    }
  },

  // notification specific errors
  INVALID_NOTIFICATION_TEMPLATE {
    @Override
    public int httpStatusCode() {
      return 400;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.VALIDATION_ERROR;
    }
  },

  INVALID_EMAIL_TEMPLATE {
    @Override
    public int httpStatusCode() {
      return 400;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.VALIDATION_ERROR;
    }
  },
  EMAIL_MODEL_FIELDS_MISSING {
    @Override
    public int httpStatusCode() {
      return 400;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.VALIDATION_ERROR;
    }
  },
  EMAIL_ATTACHMENT_NOT_SUPPORTED {
    @Override
    public int httpStatusCode() {
      return 400;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.VALIDATION_ERROR;
    }
  },

  EMAIL_MESSAGE_LOADING_MUSTACHE_ERROR {
    @Override
    public int httpStatusCode() {
      return 400;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.VALIDATION_ERROR;
    }
  },
  EMAIL_FILE_TRANSFER_ERROR {
    @Override
    public int httpStatusCode() {
      return 500;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.INTERNAL_ERROR;
    }
  },

  EMAIL_MIME_DATA_SETTING_ERROR {
    @Override
    public int httpStatusCode() {
      return 500;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.INTERNAL_ERROR;
    }
  },

  EMAIL_MIME_CREATION_ERROR {
    @Override
    public int httpStatusCode() {
      return 500;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.INTERNAL_ERROR;
    }
  },

  EMAIL_CLIENT_ERROR {
    @Override
    public int httpStatusCode() {
      return 500;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.INTERNAL_ERROR;
    }
  },
  EXTERNAL_ACCOUNT_TOKEN_NOT_FOUND {
    @Override
    public int httpStatusCode() {
      return 404;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.VALIDATION_ERROR;
    }
  },

  OLYMP_TRADE_ERROR{
    @Override
    public int httpStatusCode() {
      return 500;
    }

    @Override
    public ErrorType errorType() {
      return ErrorType.INTERNAL_ERROR;
    }
  }
}
