package com.netease.backend.coordinator.id.db;

import com.netease.backend.coordinator.id.IdForCoordinator;
import com.netease.backend.coordinator.id.UUIDGenerator;
import com.netease.backend.coordinator.recover.RecoverManager;

public class UuidGeneratorImp implements UUIDGenerator {
	private IdForCoordinator serverIdDist = null;
	private SequenceIdGenerator seqGen = new SequenceIdGenerator();
	
	public UuidGeneratorImp(IdForCoordinator serverIdDist, RecoverManager recoverManager) {
		seqGen.setSeqId(recoverManager.getLastMaxUUID() & SequenceIdGenerator.sequenceIdMask);
		this.serverIdDist = serverIdDist;
	}

	public IdForCoordinator getServerIdDist() {
		return serverIdDist;
	}

	public SequenceIdGenerator getSeqGen() {
		return seqGen;
	}

	public void setServerIdDist(IdForCoordinator serverIdDist) {
		this.serverIdDist = serverIdDist;
	}

	public void setSeqGen(SequenceIdGenerator seqGen) {
		this.seqGen = seqGen;
	}

	@Override
	public long next() {
		// TODO Auto-generated method stub
		int serverId = this.serverIdDist.get(); 
		long seqId = this.seqGen.nextSeqId();
		long uuid = (serverId << 48) | (seqId & SequenceIdGenerator.sequenceIdMask); 
		return uuid;
	}
}