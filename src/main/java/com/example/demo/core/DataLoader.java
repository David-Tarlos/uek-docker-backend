package com.example.demo.core;

import com.example.demo.domain.imagepost.ImagePost;
import com.example.demo.domain.imagepost.ImagePostRepository;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Initialisiert Test-Daten beim Applikationsstart.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final ImagePostRepository imagePostRepository;
    private final UserRepository userRepository;
    private int dayOffset = 0;

    public DataLoader(ImagePostRepository imagePostRepository, UserRepository userRepository) {
        this.imagePostRepository = imagePostRepository;
        this.userRepository = userRepository;
    }

    /** Lädt Test-ImagePosts falls DB leer */
    @Override
    public void run(String... args) {
        if (imagePostRepository.count() == 0) {
            loadImagePosts();
        }
    }

    /** Erstellt Test-ImagePosts für verschiedene User */
    private void loadImagePosts() {
        // Gianluca (Admin)
        createPost("ba804cb9-fa14-42a5-afaf-be488742fc54",
                "https://picsum.photos/id/1018/800/600",
                "A peaceful forest landscape with a moss-covered ground and a mysterious road disappearing into the mist.");

        createPost("ba804cb9-fa14-42a5-afaf-be488742fc54",
                "https://picsum.photos/id/1036/800/600",
                "An adventurous hiking trail through impressive mountains beneath a clear sky.");

// Olivia Parker
        createPost("0d8fa44c-54fd-4cd0-ace9-2a7da57992de",
                "https://picsum.photos/id/1022/800/600",
                "Soft green tones blend with the sky, creating a calm and almost surreal atmosphere.");

// Daniel Brooks
        createPost("8f3dd8a1-7f1a-4c62-8cb5-2c55f9e6a4a1",
                "https://picsum.photos/id/30/800/600",
                "An old coffee cup that tells stories of past times and quiet moments.");

        createPost("8f3dd8a1-7f1a-4c62-8cb5-2c55f9e6a4a1",
                "https://picsum.photos/id/1060/800/600",
                "Fresh spring flowers in full bloom, glowing in vibrant colors.");

// Mia Wallace
        createPost("1b8f87e9-1c02-4a2d-92f8-5a52f7d2e3c4",
                "https://picsum.photos/id/1043/800/600",
                "A dense forest with a quiet lake at its center, reflecting the surrounding nature.");

// Emma Wilson
        createPost("9b2f6c1e-9f3a-4d2b-8ad1-5c2a45d6f8a0",
                "https://picsum.photos/id/1015/800/600",
                "The Kjerag cliff rises dramatically above the Lysefjord.");

        createPost("9b2f6c1e-9f3a-4d2b-8ad1-5c2a45d6f8a0",
                "https://picsum.photos/id/250/800/600",
                "A camera that reflects the charm of analog photography.");

// Liam Hughes
        createPost("4c1a5f88-2d8f-4e6b-9b8f-8e1f1c0b2a31",
                "https://picsum.photos/id/292/800/600",
                "A lovingly prepared breakfast that immediately invites a relaxed start to the day.");

// Sophia Carter
        createPost("6a0e2d5c-7b92-46d2-9d8e-1c2b9f8a4d10",
                "https://picsum.photos/id/1084/800/600",
                "Grey seals with impressively long and striking teeth.");

// Jackson Lee
        createPost("a3d7c1b0-5e24-4a95-8f3c-7c2a1b4d9e6f",
                "https://picsum.photos/id/1033/800/600",
                "An airport scene featuring two escalators.");

        createPost("a3d7c1b0-5e24-4a95-8f3c-7c2a1b4d9e6f",
                "https://picsum.photos/id/1005/800/600",
                "A young woman sitting by the shore, enjoying a beautiful view of the sea.");

// Ava Morgan
        createPost("f7c2b4a1-3d6e-4b2f-9a1c-5e8d3b1a7c90",
                "https://picsum.photos/id/1042/800/600",
                "In the distance, circular rays of light can be seen, raising the question of whether they might be extraterrestrial.");

// Noah Reed
        createPost("2e8b6a4d-1f93-4c7b-9e2a-0d5f7c3a9b21",
                "https://picsum.photos/id/1080/800/600",
                "Beautiful red strawberries, fresh and vibrant.");

        createPost("2e8b6a4d-1f93-4c7b-9e2a-0d5f7c3a9b21",
                "https://picsum.photos/id/1025/800/600",
                "A dog wrapped in a cloth on a beautiful forest hiking trail — almost as adorable as Gianluca Daffré.");

        System.out.println("Loaded " + imagePostRepository.count() + " test image posts");
    }

    /** Erstellt einzelnen Post mit zeitlichem Offset */
    private void createPost(String userId, String imageUrl, String description) {
        User user = userRepository.findById(UUID.fromString(userId)).orElse(null);
        if (user != null) {
            ImagePost post = new ImagePost()
                    .setImageUrl(imageUrl)
                    .setDescription(description)
                    .setAuthor(user)
                    .setCreatedAt(LocalDateTime.now().minusDays(15 - dayOffset));
            imagePostRepository.save(post);
            dayOffset++;
        }
    }
}
