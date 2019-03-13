package org.rulez.demokracia.pdengine;

import java.util.List;

import javax.xml.ws.WebServiceContext;

import org.rulez.demokracia.pdengine.exception.ReportedException;

public class VoteRegistry extends ChoiceManager implements IVoteManager {
	public VoteRegistry(WebServiceContext wsContext) {
		super(wsContext);
	}
	
	@Override
	public String obtainBallot(String id, String adminKey) {
		Vote vote = getVote(id);
		vote.checkAdminKey(adminKey);
		String ballot = RandomUtils.createRandomKey();
		vote.ballots.add(ballot);
		return ballot;
	}

	@Override
	public void castVote(String voteId, String ballot, List<RankedChoice> theVote) {
		Vote vote = getVote(voteId);
		if(! vote.canVote) 
		  throw new IllegalArgumentException("This issue cannot be voted on yet");
		
		if (! vote.ballots.contains(ballot)) 
		  throw new IllegalArgumentException(String.format("Illegal ballot: %s", ballot));
		
		for(RankedChoice choice : theVote){
			if (! vote.choices.containsKey(choice.choiceId)) 
				throw new IllegalArgumentException(String.format("Invalid choiceId: %s", choice.choiceId));
			if (choice.rank < 0) 
				throw new IllegalArgumentException(String.format("Invalid rank: %d", choice.rank));
		}
		
		vote.ballots.remove(ballot);
	}

	@Override
	public void modifyVote(String voteId, String adminKey, String votename) throws ReportedException {
	    Validate.checkVoteName(votename);
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		vote.name = votename;
	}
	
	public void deleteVote(String voteId, String adminKey) throws ReportedException {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		session.remove(vote);
	}

	@Override
	public String deleteChoice(String voteId, String choiceId, String adminKey) throws ReportedException {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		Choice votesChoice = vote.getChoice(choiceId);
		vote.choices.remove(votesChoice.id);
		
		return "OK";
	}
	
	public void modifyChoice(String voteId, String choiceId, String adminKey, String choice) throws ReportedException {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		Choice votesChoice = vote.getChoice(choiceId);
		
		if(!vote.hasIssuedBallots())
			votesChoice.name = choice;
	}

	@Override
	public void setVoteParameters(String voteId, String adminKey, int minEndorsements, boolean canAddin,
			boolean canEndorse, boolean canVote, boolean canView) throws ReportedException {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		
		if(minEndorsements >= 0)
			vote.setParameters(adminKey, minEndorsements, canAddin, canEndorse, canVote, canView);	
		else
			throw new IllegalArgumentException(String.format("Illegal minEndorsements: %s", minEndorsements));
	}
}
