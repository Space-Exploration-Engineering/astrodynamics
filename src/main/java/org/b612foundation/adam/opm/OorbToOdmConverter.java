package org.b612foundation.adam.opm;

import static org.b612foundation.adam.astro.AstroConstants.AU_PER_DAY_TO_KM_PER_SEC;
import static org.b612foundation.adam.astro.AstroConstants.AU_TO_KM;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import org.b612foundation.adam.astro.AstroUtils;

public class OorbToOdmConverter {

  public static OrbitParameterMessage oorbFullOrbitToOpm(String orbitLine) {
    final String comment =
        "Generated by org.b612foundation.adam.opm.OorbToOdmConverter.oorbFullOrbitToOpm method";
    final int objectIdIndex = 0;
    final int xIndex = 1;
    final int yIndex = 2;
    final int zIndex = 3;
    final int vxIndex = 4;
    final int vyIndex = 5;
    final int vzIndex = 6;
    final int mjdIndex = 7;
    final int covXXIndex = 8;
    final int covYYIndex = 9;
    final int covZZIndex = 10;
    final int covXdotXdotIndex = 11;
    final int covYdotYdotIndex = 12;
    final int covZdotZdotIndex = 13;
    final int covXYIndex = 14;
    final int covXZIndex = 15;
    final int covXXdotIndex = 16;
    final int covXYdotIndex = 17;
    final int covXZdotIndex = 18;
    final int covYZIndex = 19;
    final int covYXdotIndex = 20;
    final int covYYdotIndex = 21;
    final int covYZdotIndex = 22;
    final int covZXdotIndex = 23;
    final int covZYdotIndex = 24;
    final int covZZdotIndex = 25;
    final int covXdotYdotIndex = 26;
    final int covXdotZdotIndex = 27;
    final int covYdotZdotIndex = 28;
    final int expectedElementsCount = 31;
    final double posPosCovElemAstroToSI = AU_TO_KM * AU_TO_KM;
    final double velVelCovElemAstroToSI = AU_PER_DAY_TO_KM_PER_SEC * AU_PER_DAY_TO_KM_PER_SEC;
    final double posVelCovElemAstroToSI = AU_TO_KM * AU_PER_DAY_TO_KM_PER_SEC;
    final OdmCommonMetadata.ReferenceFrame referenceFrame =
        OdmCommonMetadata.ReferenceFrame.J2000_IAU76ECLIP;
    String[] elems = orbitLine.trim().split("\\s+");
    if (elems.length != expectedElementsCount) {
      throw new IllegalArgumentException("Incorrect number of arguments on line: " + elems.length);
    }

    final LocalDateTime epoch =
        AstroUtils.localDateTimefromMJD(Double.parseDouble(elems[mjdIndex]));
    final String epochString = epoch.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    StateVector stateVector = new StateVector();
    stateVector.getComments().add(comment);
    stateVector.setX(Double.parseDouble(elems[xIndex]) * AU_TO_KM);
    stateVector.setY(Double.parseDouble(elems[yIndex]) * AU_TO_KM);
    stateVector.setZ(Double.parseDouble(elems[zIndex]) * AU_TO_KM);
    stateVector.setX_dot(Double.parseDouble(elems[vxIndex]) * AU_PER_DAY_TO_KM_PER_SEC);
    stateVector.setY_dot(Double.parseDouble(elems[vyIndex]) * AU_PER_DAY_TO_KM_PER_SEC);
    stateVector.setZ_dot(Double.parseDouble(elems[vzIndex]) * AU_PER_DAY_TO_KM_PER_SEC);
    stateVector.setEpoch(epochString);

    CartesianCovariance cartesianCovariance = new CartesianCovariance();
    cartesianCovariance.setEpoch(epochString);
    cartesianCovariance.setCov_ref_frame(referenceFrame);
    cartesianCovariance.getComments().add(comment);
    cartesianCovariance.setCx_x(Double.parseDouble(elems[covXXIndex]) * posPosCovElemAstroToSI);
    cartesianCovariance.setCy_y(Double.parseDouble(elems[covYYIndex]) * posPosCovElemAstroToSI);
    cartesianCovariance.setCz_z(Double.parseDouble(elems[covZZIndex]) * posPosCovElemAstroToSI);
    cartesianCovariance.setCx_dot_x_dot(
        Double.parseDouble(elems[covXdotXdotIndex]) * velVelCovElemAstroToSI);
    cartesianCovariance.setCy_dot_y_dot(
        Double.parseDouble(elems[covYdotYdotIndex]) * velVelCovElemAstroToSI);
    cartesianCovariance.setCz_dot_z_dot(
        Double.parseDouble(elems[covZdotZdotIndex]) * velVelCovElemAstroToSI);

    cartesianCovariance.setCy_x(Double.parseDouble(elems[covXYIndex]) * posPosCovElemAstroToSI);
    cartesianCovariance.setCz_x(Double.parseDouble(elems[covXZIndex]) * posPosCovElemAstroToSI);
    cartesianCovariance.setCx_dot_x(Double.parseDouble(elems[covXXdotIndex]) * posVelCovElemAstroToSI);
    cartesianCovariance.setCy_dot_x(Double.parseDouble(elems[covXYdotIndex]) * posVelCovElemAstroToSI);
    cartesianCovariance.setCz_dot_x(Double.parseDouble(elems[covXZdotIndex]) * posVelCovElemAstroToSI);

    cartesianCovariance.setCz_y(Double.parseDouble(elems[covYZIndex]) * posPosCovElemAstroToSI);
    cartesianCovariance.setCx_dot_y(Double.parseDouble(elems[covYXdotIndex]) * posVelCovElemAstroToSI);
    cartesianCovariance.setCy_dot_y(Double.parseDouble(elems[covYYdotIndex]) * posVelCovElemAstroToSI);
    cartesianCovariance.setCz_dot_y(Double.parseDouble(elems[covYZdotIndex]) * posVelCovElemAstroToSI);

    cartesianCovariance.setCx_dot_z(Double.parseDouble(elems[covZXdotIndex]) * posVelCovElemAstroToSI);
    cartesianCovariance.setCy_dot_z(Double.parseDouble(elems[covZYdotIndex]) * posVelCovElemAstroToSI);
    cartesianCovariance.setCz_dot_z(Double.parseDouble(elems[covZZdotIndex]) * posVelCovElemAstroToSI);

    cartesianCovariance.setCy_dot_x_dot(
        Double.parseDouble(elems[covXdotYdotIndex]) * velVelCovElemAstroToSI);
    cartesianCovariance.setCz_dot_x_dot(
        Double.parseDouble(elems[covXdotZdotIndex]) * velVelCovElemAstroToSI);

    cartesianCovariance.setCz_dot_y_dot(
        Double.parseDouble(elems[covYdotZdotIndex]) * velVelCovElemAstroToSI);

    OdmCommonHeader header = new OdmCommonHeader();
    header.setCreation_date(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

    OdmCommonMetadata metadata = new OdmCommonMetadata();
    metadata.getComments().add(comment);
    metadata.setRef_frame(referenceFrame);
    metadata.setTime_system(OdmCommonMetadata.TimeSystem.TT);
    metadata.setCenter_name(OdmCommonMetadata.CenterName.SUN);
    metadata.setObject_id(elems[objectIdIndex]);
    metadata.setObject_name(metadata.getObject_id());
    OrbitParameterMessage opm = new OrbitParameterMessage();
    opm.setState_vector(stateVector);
    opm.setCartesianCovariance(cartesianCovariance);
    opm.setHeader(header);
    opm.setMetadata(metadata);

    return opm;
  }

  public static String opmToOorbFullOrbit(OrbitParameterMessage opm, double H, double G) {
    // "5021              0.59654514357699E-01 -0.27361945126639E+01 -0.27774936502626E+00
    // 0.70760512465527E-02  0.22455411147103E-02  0.19783428192193E-02   73452.00000000
    // 0.9733632E-05   0.8131753E-05   0.5220416E-05   0.6160844E-07   0.3574345E-07   0.1843793E-07
    //   0.2857906E+00   0.4624086E+00   0.1496698E+00  -0.5459364E+00  -0.2757571E+00
    // 0.9126070E+00  -0.8804816E+00  -0.9426819E+00  -0.9066317E+00  -0.7133995E+00  -0.9073508E+00
    //  -0.9215130E+00   0.6956380E+00   0.8103546E+00   0.8520601E+00  20.00004  0.150000\n";
    String fmt =
        "%-17s%21.14E %21.14E %21.14E %21.14E %21.14E %21.14E %16.8f %15.7E %15.7E %15.7E %15.7E %15.7E %15.7E %15.7E %15.7E %15.7E %15.7E %15.7E %15.7E %15.7E %15.7E %15.7E %15.7E %15.7E %15.7E %15.7E %15.7E %15.7E %9.5f %9.6f";
    OdmCommonMetadata m = opm.getMetadata();
    StateVector pv = opm.getState_vector();
    CartesianCovariance c = opm.getCartesianCovariance();
    LocalDateTime epoch = LocalDateTime.parse(opm.getState_vector().getEpoch());
    double mjdEpoch = AstroUtils.mjdFromLocalDateTime(epoch);
    return String.format(
        fmt,
        m.getObject_id(),
        pv.getX() / AU_TO_KM,
        pv.getY() / AU_TO_KM,
        pv.getZ() / AU_TO_KM,
        pv.getX_dot() / AU_PER_DAY_TO_KM_PER_SEC,
        pv.getY_dot() / AU_PER_DAY_TO_KM_PER_SEC,
        pv.getZ_dot() / AU_PER_DAY_TO_KM_PER_SEC,
        mjdEpoch,
        c.getCx_x() / (AU_TO_KM * AU_TO_KM),
        c.getCy_y() / (AU_TO_KM * AU_TO_KM),
        c.getCz_z() / (AU_TO_KM * AU_TO_KM),
        c.getCx_dot_x_dot() / (AU_PER_DAY_TO_KM_PER_SEC * AU_PER_DAY_TO_KM_PER_SEC),
        c.getCy_dot_y_dot() / (AU_PER_DAY_TO_KM_PER_SEC * AU_PER_DAY_TO_KM_PER_SEC),
        c.getCz_dot_z_dot() / (AU_PER_DAY_TO_KM_PER_SEC * AU_PER_DAY_TO_KM_PER_SEC),
        c.getCy_x() / (AU_TO_KM * AU_TO_KM),
        c.getCz_x() / (AU_TO_KM * AU_TO_KM),
        c.getCx_dot_x() / (AU_TO_KM * AU_PER_DAY_TO_KM_PER_SEC),
        c.getCy_dot_x() / (AU_TO_KM * AU_PER_DAY_TO_KM_PER_SEC),
        c.getCz_dot_x() / (AU_TO_KM * AU_PER_DAY_TO_KM_PER_SEC),
        c.getCz_y() / (AU_TO_KM * AU_TO_KM),
        c.getCx_dot_y() / (AU_TO_KM * AU_PER_DAY_TO_KM_PER_SEC),
        c.getCy_dot_y() / (AU_TO_KM * AU_PER_DAY_TO_KM_PER_SEC),
        c.getCz_dot_y() / (AU_TO_KM * AU_PER_DAY_TO_KM_PER_SEC),
        c.getCx_dot_z() / (AU_TO_KM * AU_PER_DAY_TO_KM_PER_SEC),
        c.getCy_dot_z() / (AU_TO_KM * AU_PER_DAY_TO_KM_PER_SEC),
        c.getCz_dot_z() / (AU_TO_KM * AU_PER_DAY_TO_KM_PER_SEC),
        c.getCy_dot_x_dot() / (AU_PER_DAY_TO_KM_PER_SEC * AU_PER_DAY_TO_KM_PER_SEC),
        c.getCz_dot_x_dot() / (AU_PER_DAY_TO_KM_PER_SEC * AU_PER_DAY_TO_KM_PER_SEC),
        c.getCz_dot_y_dot() / (AU_PER_DAY_TO_KM_PER_SEC * AU_PER_DAY_TO_KM_PER_SEC),
        H,
        G);
  }

  public static void opmToOorbFile(OrbitParameterMessage opm, double H, double G, Path orbFilePath)
      throws FileNotFoundException {
    String[] header = {
      "#    Number           Ecliptic x            Ecliptic y            Ecliptic z          Ecliptic dx/dt        Ecliptic dy/dt        Ecliptic dz/dt        Epoch (TT)       sigma e1        sigma e2        sigma e3        sigma e4        sigma e5        sigma e6       cor(e1,e2)      cor(e1,e3)      cor(e1,e4)      cor(e1,e5)      cor(e1,e6)      cor(e2,e3)      cor(e2,e4)      cor(e2,e5)      cor(e2,e6)      cor(e3,e4)      cor(e3,e5)      cor(e3,e6)      cor(e4,e5)      cor(e4,e6)      cor(e5,e6)    Absolute   Slope",
      "#      or                                                                                                                                                  MJD                                                                                                                                                                                                                                                                                                                                                        magnitude parameter",
      "#  designation           [au]                  [au]                  [au]                 [au/d]                [au/d]                [au/d]                                                                                                                                                                                                                                                                                                                                                                              H         G",
      "#-----0001-----<>--------0039--------<>--------0040--------<>--------0041--------<>--------0042--------<>--------0043--------<>--------0044--------<>------0074-----<>-----0009-----<>-----0011-----<>-----0010-----<>-----0012-----<>-----0013-----<>-----0014-----<>-----0015-----<>-----0016-----<>-----0017-----<>-----0018-----<>-----0019-----<>-----0020-----<>-----0021-----<>-----0022-----<>-----0023-----<>-----0024-----<>-----0025-----<>-----0026-----<>-----0027-----<>-----0028-----<>-----0029-----<>---0036-<>---0037-<"
    };
    String stateLine = opmToOorbFullOrbit(opm, H, G);
    try (PrintWriter writer = new PrintWriter(orbFilePath.toFile())) {
      Arrays.stream(header).forEach(writer::println);
      writer.println(stateLine);
    }
  }
}
