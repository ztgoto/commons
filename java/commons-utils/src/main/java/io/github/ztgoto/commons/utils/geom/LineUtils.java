package io.github.ztgoto.commons.utils.geom;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.locationtech.jts.simplify.DouglasPeuckerSimplifier;

public class LineUtils {
	
	private static GeometryFactory GF= new GeometryFactory();
	
	/**
	 * 将路线点压缩
	 * @param points
	 * @param distanceTolerance 偏差（数值越大压缩比越高，精度损失越大。如经纬度，可以用 0.0001）
	 * @return
	 */
	public static double[][] compress(double[][] points, double distanceTolerance) {
		
		if (points == null || points.length == 0) {
			return new double[0][0];
		}
		Coordinate[] coordinates = new Coordinate[points.length];
		for (int i = 0; i < points.length; i++) {
			double[] point = points[i];
			if (point == null || point.length != 2) {
				throw new RuntimeException("point["+i+"] data format error");
			}
			coordinates[i] = new Coordinate(point[0], point[1]);
		}
		
		LineString resultLine = compress(coordinates, distanceTolerance);
		
		coordinates = resultLine.getCoordinates();
		double[][] result = new double[coordinates.length][2];
		for (int i = 0; i < result.length; i++) {
			result[i][0] = coordinates[i].x;
			result[i][1] = coordinates[i].y;
		}
		
		return result;
	}
	
	/**
	 * 将路线点压缩
	 * @param coordinates 点
	 * @param distanceTolerance 偏差（数值越大压缩比越高，精度损失越大。如经纬度，可以用 0.0001）
	 * @return
	 */
	public static LineString compress(Coordinate[] coordinates, double distanceTolerance) {
		
		CoordinateSequence cs = new CoordinateArraySequence(coordinates);
		
		LineString source = new LineString(cs, GF);
		
		return (LineString) DouglasPeuckerSimplifier.simplify(source,distanceTolerance);
	}
	
	public static LineString compress(Coordinate[] coordinates, double distanceTolerance, GeometryFactory factory) {
		
		CoordinateSequence cs = new CoordinateArraySequence(coordinates);
		
		LineString source = new LineString(cs, factory);
		
		return (LineString) DouglasPeuckerSimplifier.simplify(source,distanceTolerance);
	}

}
