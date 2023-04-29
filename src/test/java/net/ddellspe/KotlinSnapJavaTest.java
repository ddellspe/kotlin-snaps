package net.ddellspe;

import com.snaplogic.snap.test.harness.SnapTestRunner;
import com.snaplogic.snap.test.harness.TestFixture;
import org.junit.runner.RunWith;

@RunWith(SnapTestRunner.class)
@TestFixture(
    snap = KotlinSnap.class,
    input = "data/kotlin_snap/input_data.json",
    expectedOutputPath = "data/kotlin_snap/expected",
    properties = "data/kotlin_snap/default_properties.json")
public class KotlinSnapJavaTest {

  @TestFixture()
  public void kotlinSnap_runStandardWithInputDoc() {}

  @TestFixture(
      propertyOverrides = {
        "$.settings.propKey.expression",
        "false",
        "$.settings.propValue.expression",
        "false"
      })
  public void kotlinSnap_runStandardWithInputDocNoExpression() {}
}
