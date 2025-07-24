package com.clutchcoders.dao;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

import java.util.concurrent.TimeUnit;

public class GetDAO {
    public String getOwnerRoomImageURL(String fileName) throws Exception {
        // Get default bucket from Firebase Admin SDK
        Bucket bucket = StorageClient.getInstance().bucket();

        // Reconstruct object path (should match what was used while uploading)
        String objectName = "Owner_Room/" + fileName;

        // Get the blob (the file) from the bucket
        Blob blob = bucket.get(objectName);

        if (blob == null) {
            throw new Exception("Image not found in Firebase Storage.");
        }

        // Generate signed URL valid for ~100 years
        return blob.signUrl(36500, TimeUnit.DAYS).toString();
    }

    public String getBachelorRoomImageURL(String fileName) throws Exception {
        // Get default bucket from Firebase Admin SDK
        Bucket bucket = StorageClient.getInstance().bucket();

        // Reconstruct object path (should match what was used while uploading)
        String objectName = "Bachelor_Room/" + fileName;

        // Get the blob (the file) from the bucket
        Blob blob = bucket.get(objectName);

        if (blob == null) {
            throw new Exception("Image not found in Firebase Storage.");
        }

        // Generate signed URL valid for ~100 years
        return blob.signUrl(36500, TimeUnit.DAYS).toString();
    }


    public String getOwnerProfileImageURL(String fileName) throws Exception {
        // Get default bucket from Firebase Admin SDK
        Bucket bucket = StorageClient.getInstance().bucket();

        // Reconstruct object path (should match what was used while uploading)
        String objectName = "Owner_Profile/" + fileName;

        // Get the blob (the file) from the bucket
        Blob blob = bucket.get(objectName);

        if (blob == null) {
            throw new Exception("Image not found in Firebase Storage.");
        }

        // Generate signed URL valid for ~100 years
        return blob.signUrl(36500, TimeUnit.DAYS).toString();
    }

    public String getBacahelorProfileImageURL(String fileName) throws Exception {
        // Get default bucket from Firebase Admin SDK
        Bucket bucket = StorageClient.getInstance().bucket();

        // Reconstruct object path (should match what was used while uploading)
        String objectName = "Bachelor_Profile/" + fileName;

        // Get the blob (the file) from the bucket
        Blob blob = bucket.get(objectName);

        if (blob == null) {
            throw new Exception("Image not found in Firebase Storage.");
        }

        // Generate signed URL valid for ~100 years
        return blob.signUrl(36500, TimeUnit.DAYS).toString();
    }
}

