package org.b612foundation.adam.datamodel.estimation;

import java.util.List;
import java.util.Map;
import lombok.*;
import org.b612foundation.adam.datamodel.AdamObject;
import org.b612foundation.adam.opm.OrbitParameterMessage;

/**
 * The parameters to use for a specific run of the OD software which includes things like the space
 * object ID, measurements to process, etc.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public final class OrbitDeterminationParameters extends AdamObject {
  /** Logging level for output of forensic data during OD run */
  private int loggingLevel;

  /**
   * Settings which may be specific to an OD calculation at initialization time, like initial
   * guesses which are state specific
   */
  @Singular private Map<String, String> initializationSettings;

  /** An initial state which can be used for initializing the OD system */
  private OrbitParameterMessage initialStateEstimate;

  /** The SRP coefficient for the space object */
  private double initialStateEstimateCr;

  /** The SRP coefficient for the space object */
  private double initialStateEstimateSrpArea;

  /** The initial state estimate of the mass of the space object */
  private double initialStateEstimateMass;

  /** Format of the passed in measurements string */
  private String measurementsFormatType;

  /**
   * String representation of the measurements to be processed in this OD. Format needs to
   * correspond to accepted formats and match the passed in measurement format. This is often Base64
   * encoded. Examples include: - Base64 encoded ZTF-Avro format file - Base64 DES File
   */
  private String measurements;

  /** The Indices of observations to subsample (or empty if want all */
  @Singular private List<Integer> measurementSampleIndices;

  /**
   * Indicates whether to subsample measurements, assuming there are indices specified, only only
   * bootstrapping passes rather than on all passes
   */
  private boolean subSampleMeasurementsBootstrappingOnly;
  /**
   * Indicates whether to use a simplified force model during bootstrapping. The simplification is
   * up to the OD tool that is implemented. For example, switching to a two-body propagation for the
   * first pass of an IOD.
   */
  private boolean bootstrapWithSimplifiedModel;

  /** Settings for the numeric propagator - the ID. */
  private String orbitDeterminationConfigUuid;

  /** The ID in the observations file that we are doing the OD against */
  private String spaceObjectId;

  /** The ID(s) of the observers that are in the file */
  @Singular private List<String> observerIds;

  /** The Coordinate frame that the resulting orbit should be calculated in */
  private String outputFrame;

  private OdType type;

  public enum OdType {
    INITIAL,
    FULL
  }

  public enum MeasurumentType {
    LSST_CSV_FILE_PATH,
    DES_FILE_PATH
  }
}
