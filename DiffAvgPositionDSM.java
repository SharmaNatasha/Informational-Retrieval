package uk.ac.gla.dcs.dsms;

import org.terrier.structures.postings.BlockPosting;
import org.terrier.structures.postings.IterablePosting;

import java.util.ArrayList;
import java.util.Arrays;

import org.terrier.matching.dsms.DependenceScoreModifier;

/** 
 * find the position of the of the term and sum up all the position. 
 * Average the sum of each query term using term frequency and store in a list.
 * and find the difference between average position of terms. 
 */
public class DiffAvgPositionDSM extends DependenceScoreModifier {


	/** This class is passed the postings of the current document,
	 * and return a score to represent that document.
	 */
	@Override
	protected double calculateDependence(
			IterablePosting[] ips,
			boolean[] okToUse,
			double[] phraseTermWeights, boolean SD 
		) 
	{
		
		final int numberOfQueryTerms = okToUse.length;
		
		ArrayList<Double> avgTermWeight=new ArrayList<Double>();	
	
		for(int queryIndex=0; queryIndex< numberOfQueryTerms ; queryIndex++)
		{
			double termWeight =0.0;
			
			if(okToUse[queryIndex] == true)
			{
			   int [] term = ((BlockPosting) ips[queryIndex]).getPositions();
			   for(int termIndex=0; termIndex<term.length; termIndex++)
			   {
				   termWeight = termWeight + term[termIndex];
			   }
		           avgTermWeight.add(termWeight/term.length);
			}       
		}
		
		int avgTermIndex = 0;
		double diffPosition = 0.0;
		int count =0;
		
		while(avgTermIndex < avgTermWeight.size()) 
		{ 
		   int pairIndex = avgTermIndex + 1;
		   
		   while(pairIndex < avgTermWeight.size())
		   {
			   diffPosition = diffPosition + Math.abs(avgTermWeight.get(avgTermIndex) - avgTermWeight.get(pairIndex));
			   pairIndex++;
			   count++;
			
		   }
		   avgTermIndex++;
		   
		}
		return diffPosition/count;
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
