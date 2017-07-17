package uk.ac.gla.dcs.dsms;

import org.terrier.structures.postings.BlockPosting;
import org.terrier.structures.postings.IterablePosting;

import java.util.ArrayList;
import java.util.Arrays;

import org.terrier.matching.dsms.DependenceScoreModifier;

/** 
 * Separate the posting list into two terms, term1 and term2 
 * The calculate distance between two terms 
 * Divide by the product of both term’s length
 */
public class AvgDistanceDSM extends DependenceScoreModifier {

	/** This class is passed the postings of the current document,
	 * and should return a score to represent that document.
	 */
	@Override
	protected double calculateDependence(
			IterablePosting[] ips, 
			boolean[] okToUse,  
			double[] phraseTermWeights, boolean SD 
		) 
	{
		
		final int numberOfQueryTerms = okToUse.length;
		
		double avgDist = 0.0;
		
		for(int queryIndex=0; queryIndex< numberOfQueryTerms ; queryIndex++)
		{
			if(okToUse[queryIndex] == true)
			{
			   int [] term1 = ((BlockPosting)ips[queryIndex]).getPositions();	
			   
			   for(int queryIndex2= queryIndex; queryIndex2< numberOfQueryTerms && queryIndex2+1 < numberOfQueryTerms ; queryIndex2++)
			   {
				   if(okToUse[queryIndex2+1] == true)
				   {
				      int [] term2 = ((BlockPosting) ips[queryIndex2 + 1]).getPositions(); 
				      
				      double calDistance = 0.0;
				      
				      for(int i=0; i< term1.length; i++)
				      {
					    for(int j=0; j < term2.length; j++)
					    {
					    	calDistance = calDistance + Math.abs((term1[i] - term2[j]));
					    }  
				      }
				      avgDist = avgDist + (calDistance/((term1.length)*(term2.length)));
				       
				   }   
			   }	
			}
		}
		
		return avgDist/numberOfQueryTerms;
	}

	@Override
	protected double scoreFDSD(int matchingNGrams, int docLength) {
		throw new UnsupportedOperationException();
	}


	@Override
	public String getName() {
		return "ProxFeatureDSM_MYFUNCTION";
	}
	
}
