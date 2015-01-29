package com.google.android.gms.drive;

import android.os.Bundle;
import android.os.Parcel;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveApi.ContentsResult;
import com.google.android.gms.drive.DriveApi.DriveIdResult;
import com.google.android.gms.drive.DriveApi.MetadataBufferResult;
import droidsafe.annotations.*;

import droidsafe.helpers.DSOnlyType;
import droidsafe.helpers.DSUtils;

public class DroidsafeDriveApiMultiResult implements Result, DriveIdResult,
		MetadataBufferResult, ContentsResult {

	Parcel p;
	
	@DSBan(DSCat.DROIDSAFE_INTERNAL)
	public DroidsafeDriveApiMultiResult(DSOnlyType dontcare, int taint) {
		p = new Parcel(dontcare);
		addTaint(taint);
	}

	@DSSafe(DSCat.SAFE_LIST)
	@DSSource({DSSourceKind. GOOGLE_SERVICES})
	@Override
	public Contents getContents() {
		// TODO Auto-generated method stub
		Contents contents = Contents.CREATOR.createFromParcel(p);
		contents.addTaint(getTaint());
		return contents;
	}

	@DSSafe(DSCat.SAFE_LIST)
	
	@Override
	public MetadataBuffer getMetadataBuffer() {
		// TODO Auto-generated method stub
		DataHolder holder = new DataHolder(new String[] {DSUtils.FAKE_STRING}, null, getTaintInt(), new Bundle());
		MetadataBuffer meta = new MetadataBuffer(holder, toString());
		return meta;
	}

	@DSSafe(DSCat.SAFE_OTHERS)
	@DSSource({DSSourceKind.GOOGLE_DRIVE})
	@Override
	public DriveId getDriveId() {
		// TODO Auto-generated method stub
		DriveId id = new DriveId(DSUtils.FAKE_STRING, getTaintInt(), 0);
		return id;
	}

	@DSSafe(DSCat.SAFE_LIST)
	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		Status stat = new Status(getTaintInt());
		return stat;
	}

}
