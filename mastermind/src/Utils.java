class Utils {

  // checks if the range of length of guesses is valid
  int checkRange(int length, String msg) {
    if (length <= 0) {
      throw new IllegalArgumentException(msg);
    }
    else {
      return length;
    }
  }

  // checks if the range of length of sequence is valid
  int checkRange(int lengthOne, int lengthTwo, boolean hasDuplicate, String msg) {
    if (lengthOne <= 0 || lengthTwo <= 0 || lengthTwo < lengthOne && !hasDuplicate) {
      throw new IllegalArgumentException(msg);
    }
    else {
      return lengthOne;
    }
  }

  // checks if the length of colors is valid
  ILoColors checkColors(int lengthOne, int lengthTwo, boolean hasDuplicate, ILoColors colors,
      String msg) {
    if (lengthOne <= 0 || lengthTwo <= 0 || lengthTwo < lengthOne && !hasDuplicate) {
      throw new IllegalArgumentException(msg);
    }
    else {
      return colors;
    }
  }
}