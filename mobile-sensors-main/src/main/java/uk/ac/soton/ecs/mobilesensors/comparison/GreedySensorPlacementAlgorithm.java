package uk.ac.soton.ecs.mobilesensors.comparison;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.StopWatch;

import uk.ac.soton.ecs.mobilesensors.RMSEMetric;
import uk.ac.soton.ecs.mobilesensors.layout.Location;

public class GreedySensorPlacementAlgorithm extends
		AbstractSensorPlacementAlgorithm {

	@Override
	protected void calculate() {
		StopWatch watch = new StopWatch();
		watch.start();

		List<Long> time = new ArrayList<Long>();

		RMSEMetric rmseMetric = new RMSEMetric();
		rmseMetric.initialize(simulation);

		for (int i = 0; i < sensorCount; i++) {
			Set<Location> selected = new HashSet<Location>();

			double bestValue = Double.NEGATIVE_INFINITY;
			Location bestLocation = null;

			for (Location location : graph.getLocations()) {
				double value = informativenessFunction.getInformativeness(
						location, selected);

				if (value > bestValue) {
					bestLocation = location;
					bestValue = value;
				}
			}

			// rmseMetric.handleEndOfRound(null, null, 0);

			time.add(watch.getTime());

			selected.add(bestLocation);
			addBestLocation(bestLocation);
			informativenessFunction.observe(bestLocation);

		}

		watch.stop();

		System.out.println(time);

		// System.exit(0);

	}

}
