package org.eclipse.january.dataset;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.january.asserts.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MathsArrayTypeAbsFunctionParameterizeTest {
	@Parameters(name = "{index}: {0}")
	public static Collection<Object> data() {
		return Arrays.asList(
				new Object[] { CompoundFloatDataset.class, CompoundDoubleDataset.class, CompoundByteDataset.class,
						CompoundShortDataset.class, CompoundIntegerDataset.class, CompoundLongDataset.class });
	}

	@Parameter
	public Class<? extends CompoundDataset> classType;

	private final static double ABSERRD = 1e-8;

	@Test
	public void test() throws Exception {
		Class<? extends CompoundDataset> class1 = classType;		

		// Test for multiple elements per item entry
		byte[][] baM = { { 0, 8, -1, 2, 3, 5, 6, 41, 7, 95, 8, -74, 9, 41, 11 },
				{ 0, 8, -1, 2, 3, 5, 6, 41, 7, 95, 8, -74, 9, 41, 11 } };
		Dataset aM = DatasetFactory.createFromObject(baM);
		CompoundDataset inputM = DatasetUtils.createCompoundDataset(class1, aM);

		int sizeEntry[] = aM.getShape();
		double[][] cM = new double[sizeEntry[0]][sizeEntry[1]];
		for (int i = 0; i < sizeEntry[0]; i++) {
			for (int j = 0; j < sizeEntry[1]; j++) {
				double abs = Math.abs(baM[i][j]);
				cM[i][j] = abs;
			}
		}
		CompoundDataset expectedResultM = DatasetUtils.createCompoundDataset(class1,
				DatasetFactory.createFromObject(cM));
		CompoundDataset outputM = DatasetUtils.createCompoundDataset(class1,
				DatasetFactory.createFromObject(new byte[][] { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } }));

		Dataset actualM = Maths.abs(inputM, outputM);
		CompoundDataset actualResultM = DatasetUtils.createCompoundDataset(actualM);
		TestUtils.assertDatasetEquals(expectedResultM, actualResultM, true, ABSERRD, ABSERRD);
	}
	
	
}
