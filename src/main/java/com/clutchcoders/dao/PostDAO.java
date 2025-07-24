package com.clutchcoders.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

public class PostDAO {

    public String OwnerRoomuploadImage(File file) throws Exception {
        // Get default bucket from Firebase Admin SDK
        Bucket bucket = StorageClient.getInstance().bucket();

        // Generate unique object name
        String objectName = "Owner_Room/" + UUID.randomUUID() + "-" + file.getName();

        try (InputStream fileStream = new FileInputStream(file)) {
            // Upload the file to Firebase Storage
            Blob blob = bucket.create(objectName, fileStream, "image/jpeg");

            // Generate a signed URL valid for ~100 years
            return blob.signUrl(36500, TimeUnit.DAYS).toString();
        }
    }

    public String BachelorRoomuploadImage(File file) throws Exception {
        // Get default bucket from Firebase Admin SDK
        Bucket bucket = StorageClient.getInstance().bucket();

        // Generate unique object name
        String objectName = "Bachelor_Room/" + UUID.randomUUID() + "-" + file.getName();

        try (InputStream fileStream = new FileInputStream(file)) {
            // Upload the file to Firebase Storage
            Blob blob = bucket.create(objectName, fileStream, "image/jpeg");

            // Generate a signed URL valid for ~100 years
            return blob.signUrl(36500, TimeUnit.DAYS).toString();
        }
    }

    public String OwnerProfileuploadImage(File file) throws Exception {
        // Get default bucket from Firebase Admin SDK
        Bucket bucket = StorageClient.getInstance().bucket();

        // Generate unique object name
        String objectName = "OwnerProfile/" + UUID.randomUUID() + "-" + file.getName();

        try (InputStream fileStream = new FileInputStream(file)) {
            // Upload the file to Firebase Storage
            Blob blob = bucket.create(objectName, fileStream, "image/jpeg");

            // Generate a signed URL valid for ~100 years
            return blob.signUrl(36500, TimeUnit.DAYS).toString();
        }
    }

    public String BachelorImageuploadImage(File file) throws Exception {
        // Get default bucket from Firebase Admin SDK
        Bucket bucket = StorageClient.getInstance().bucket();

        // Generate unique object name
        String objectName = "Bachelor_Profile/" + UUID.randomUUID() + "-" + file.getName();

        try (InputStream fileStream = new FileInputStream(file)) {
            // Upload the file to Firebase Storage
            Blob blob = bucket.create(objectName, fileStream, "image/jpeg");

            // Generate a signed URL valid for ~100 years
            return blob.signUrl(36500, TimeUnit.DAYS).toString();
        }
    }
}
