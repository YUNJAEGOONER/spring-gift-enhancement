package gift.exception.member;

import gift.exception.ErrorCode;
import gift.exception.MyException;

public class MemberNotFoundException extends MyException {

  public MemberNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }

}
