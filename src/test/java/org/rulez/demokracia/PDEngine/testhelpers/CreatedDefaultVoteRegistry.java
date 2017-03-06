package org.rulez.demokracia.PDEngine.testhelpers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.rulez.demokracia.PDEngine.IVoteManager;
import org.rulez.demokracia.PDEngine.VoteRegistry;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;
import org.rulez.demokracia.PDEngine.exception.ReportedException;

public class CreatedDefaultVoteRegistry extends ThrowableTester{

	public IVoteManager voteManager;
	public VoteAdminInfo adminInfo;
	public String voteName = "VoteInitialValuesTest";

	protected List<String> neededAssurances;
	protected List<String> countedAssurances;
	protected boolean isPrivate;
	protected int minEndorsements;

	@Before
	public void setUp() throws ReportedException {
		voteManager = new VoteRegistry();
		neededAssurances = new ArrayList<>();
		countedAssurances = new ArrayList<>();
		isPrivate = true;
		minEndorsements = 0;
		neededAssurances.add("magyar");
        voteName = "testVote";
		adminInfo = createAVote();
	}

	protected VoteAdminInfo createAVote() throws ReportedException {
		return voteManager.createVote(voteName, neededAssurances, countedAssurances, isPrivate, minEndorsements );
	}

}