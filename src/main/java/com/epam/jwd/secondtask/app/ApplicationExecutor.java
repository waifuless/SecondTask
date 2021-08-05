package com.epam.jwd.secondtask.app;

import com.epam.jwd.secondtask.model.Plane;
import com.epam.jwd.secondtask.services.calculation.AngleOfPlanesCalculator;
import com.epam.jwd.secondtask.services.fileworking.FileExecutorsFactory;
import com.epam.jwd.secondtask.services.fileworking.PlaneReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.jwd.secondtask.services.calculation.PerpendicularityCalculator;

import java.io.IOException;
import java.util.function.Function;

public class ApplicationExecutor {

    private final static Function<Plane, Boolean> COMMON_PERPENDICULAR_FUNCTION
            = PerpendicularityCalculator::isPlanePerpendicularOxz;
    private final static String COMMON_PERPENDICULAR_INFO_MCG = "\tPlane perpendicular with OXZ: {}";
    private final static Plane COMMON_PLANE_FOR_ANGLE_CALCULATE = AngleOfPlanesCalculator.OXY_PLANE;
    private final static String COMMON_ANGLE_INFO_MCG = "\tPlane angle with OXY: {}";

    private final static String PLANE_WAS_NOT_CREATED_MCG = "The plane was not created";
    private final static Logger LOG = LogManager.getLogger(ApplicationExecutor.class);

    public static void findPlanesInFile(String filePath) {

        LOG.trace("Program start");
        try {
            FileExecutorsFactory executorsFactory = FileExecutorsFactory.create();
            PlaneReader planeReader = executorsFactory.makeReader(filePath);
            Plane plane;
            while (planeReader.hesNextPlane()) {
                try {
                    plane = planeReader.nextPlane();
                    LOG.info(plane);
                    LOG.info(COMMON_PERPENDICULAR_INFO_MCG,
                            COMMON_PERPENDICULAR_FUNCTION.apply(plane));
                    LOG.info(COMMON_ANGLE_INFO_MCG, AngleOfPlanesCalculator
                            .calculateAngleBetweenPlanes(plane, COMMON_PLANE_FOR_ANGLE_CALCULATE));
                } catch (Exception ex) {
                    LOG.error(ex.getMessage(), ex);
                    LOG.warn(PLANE_WAS_NOT_CREATED_MCG);
                }
            }
        } catch (IOException ex) {
            LOG.error(ex);
        }
        LOG.trace("Program end");
    }
}