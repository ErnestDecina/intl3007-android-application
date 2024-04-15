package com.ernestjohndecina.intl3007_diary_application;

import static junit.framework.TestCase.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ernestjohndecina.intl3007_diary_application.database.entities.DiaryEntry;
import com.ernestjohndecina.intl3007_diary_application.layers.security_layer.SecurityLayer;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.features.DiaryFeatures;

public class DiaryFeaturesUnitTest {

    @Mock
    Activity mockActivity;

    @Mock
    ExecutorService mockExecutorService;

    @Mock
    SecurityLayer mockSecurityLayer;

    private DiaryFeatures diaryFeatures;

    // Setup method to initialize mocks and the DiaryFeatures instance before each test
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        diaryFeatures = new DiaryFeatures(mockActivity, mockExecutorService, mockSecurityLayer);
    }

    // Test the createDiaryEntry method to ensure it submits a task to the executor
    @Test
    public void testCreateDiaryEntry() {
        // Arrange: Define parameters to pass to createDiaryEntry method
        String title = "Test Title";
        String content = "Test Content";
        String timestamp = "2024-04-15";
        String location = "Test Location";
        String lastUpdate = "2024-04-15T12:00:00";
        Integer mood = 5;
        ArrayList<Uri> uriArrayList = new ArrayList<>();

        // Act: Call the createDiaryEntry method
        diaryFeatures.createDiaryEntry(title, content, timestamp, location, lastUpdate, mood, uriArrayList);

        // Assert: Verify that a task was submitted to the ExecutorService
        verify(mockExecutorService).submit(any(Runnable.class));
        // Additional assertions might be needed to check the Runnable's behavior
    }

    // Test the getAllDiaryEntries method to ensure it returns the expected list
    @Test
    public void testGetAllDiaryEntries() throws ExecutionException, InterruptedException {
        // Arrange: Create a mock list of diary entries
        List<DiaryEntry> expectedEntries = new ArrayList<>();
        when(mockSecurityLayer.decryptAllDiaryEntry()).thenReturn(expectedEntries);

        // Act: Call the getAllDiaryEntries method
        List<DiaryEntry> actualEntries = diaryFeatures.getAllDiaryEntries();

        // Assert: Verify the result is as expected
        assertEquals(expectedEntries, actualEntries);
    }

    // Test the getDiaryEntryImages method to ensure it returns the expected image list
    @Test
    public void testGetDiaryEntryImages() {
        // Arrange: Create a mock diary entry and expected images
        DiaryEntry mockEntry = new DiaryEntry();
        ArrayList<Bitmap> expectedImages = new ArrayList<>();
        when(mockSecurityLayer.decryptImages(mockEntry)).thenReturn(expectedImages);

        // Act: Call the getDiaryEntryImages method
        ArrayList<Bitmap> actualImages = diaryFeatures.getDiaryEntryImages(mockEntry);

        // Assert: Verify the result is as expected
        assertEquals(expectedImages, actualImages);
    }

    // Test the getDiaryEntryAudio method to ensure it returns the expected audio data
    @Test
    public void testGetDiaryEntryAudio() {
        // Arrange: Create a mock diary entry and expected audio data
        DiaryEntry mockEntry = new DiaryEntry();
        byte[] expectedAudio = new byte[10];
        when(mockSecurityLayer.decryptAudio(mockEntry)).thenReturn(expectedAudio);

        // Act: Call the getDiaryEntryAudio method
        byte[] actualAudio = diaryFeatures.getDiaryEntryAudio(mockEntry);

        // Assert: Verify the result is as expected
        assertArrayEquals(expectedAudio, actualAudio);
    }
}