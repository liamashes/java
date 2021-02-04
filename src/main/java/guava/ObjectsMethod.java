package guava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

public class ObjectsMethod {

  public class Sample {
    public void test() {
      // 自动进行null的校验
      Objects.equal("a", "a");
      Objects.equal("a", null);
      Objects.equal(null, "a");
      Objects.equal(null, null);

      // 顺序敏感
      Objects.hashCode("asdads", "asdasdasd", "1231d1");

      // toString
      // Returns "ClassName{x=1}"
      MoreObjects.toStringHelper(this).add("x", 1).toString();
      // Returns "MyObject{x=1}"
      MoreObjects.toStringHelper("ObjectsMethod.Sample").add("x", 1).toString();
    }

    class Person {

      private String lastName;

      private String firstName;

      private int zipCode;

      // compare/compareTo
      public int compareTo(Person other) {

        int cmp = lastName.compareTo(other.lastName);

        if (cmp != 0) {

          return cmp;
        }
        cmp = firstName.compareTo(other.firstName);
        if (cmp != 0) {
          return cmp;
        }
        return Integer.compare(zipCode, other.zipCode);
      }

      // compare/compareTo
      public int compareToNew(Person other) {
        return ComparisonChain.start()
            .compare(this.firstName, other.firstName)
            .compare(this.lastName, other.lastName)
            .compare(this.zipCode, other.zipCode)
            .result();
      }
    }
  }
}
