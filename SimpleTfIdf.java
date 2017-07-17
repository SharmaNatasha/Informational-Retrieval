package uk.ac.gla.dcs.models;

import org.terrier.matching.models.WeightingModel;
import org.terrier.matching.models.WeightingModelLibrary;

/** Simple TF-IDF to calculate the weight of a term in document
  * @author TODO
  */
public class SimpleTfIdf extends WeightingModel
{
	public String getInfo() { return this.getClass().getSimpleName(); }
	
	public double score(double tf, double docLength) {
		double weightOfTermDocument  =0.0;		
		weightOfTermDocument  = (tf)*(WeightingModelLibrary.log(((numberOfDocuments - documentFrequency) +0.5)/(documentFrequency + 0.5)));                                                         
		return weightOfTermDocument ;
	}

	public double score(double tf, double docLength, double n_t, double F_t, double _keyFrequency) {
		throw new UnsupportedOperationException();
	}
}
