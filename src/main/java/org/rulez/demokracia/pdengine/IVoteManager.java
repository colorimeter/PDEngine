package org.rulez.demokracia.pdengine;

import java.util.Set;

import org.rulez.demokracia.pdengine.dataobjects.ChoiceEntity;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;

public interface IVoteManager {

	static IVoteManager getVoteManager() {
		return VoteManagerRegistry.getVoteManager();
	}

	VoteAdminInfo createVote(String voteName, Set<String> neededAssurances, Set<String> countedAssurances,
			boolean isPrivate, int minEndorsements) throws ReportedException;

	Vote getVote(String voteId);

	String addChoice(String adminKey, String voteId, String choiceName, String user);

	ChoiceEntity getChoice(String voteId, String choiceId);

	void endorseChoice(String proxyUserName, String adminKey, String voteId, String choiceId, String userName);

	String obtainBallot(String id, String adminKey);

}