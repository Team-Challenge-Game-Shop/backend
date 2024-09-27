package com.gameshop.ecommerce.web.generator.service;

import com.gameshop.ecommerce.web.product.dao.BrandDAO;
import com.gameshop.ecommerce.web.product.dao.CategoryDAO;
import com.gameshop.ecommerce.web.product.dao.ProductDAO;
import com.gameshop.ecommerce.web.product.model.*;
import com.gameshop.ecommerce.web.review.model.Review;
import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductGeneratorService {
    private static final String REVIEW_TEXT = "I've been using the QuantumX Pro for a month now, and I'm blown away by " +
            "its precision and customizable RGB lighting. The ergonomic design is perfect for my long gaming sessions. " +
            "A must-have for serious gamers!";
    private final ProductDAO productDAO;
    private final BrandDAO brandDAO;
    private final CategoryDAO categoryDAO;
    private final UserRepository userRepository;
    private final List<String> categories = List.of("Keyboards", "Mice", "Headsets", "Mouse mats", "Joysticks and controllers", "Gaming chairs");
    private final List<String> brands = List.of("Logitech", "Razer", "Acer", "Asus", "Gigabyte", "MSI");
    private final Map<String, String> images = new HashMap<>(Map.of("Mouse", "https://girlsonlytravel.com/img/works/mouse.png",
            "Keyboard", "https://girlsonlytravel.com/img/works/keyboard.png", "Headset",
            "https://girlsonlytravel.com/img/works/headset.png", "Joystick",
            "https://girlsonlytravel.com/img/works/joystick.png",
            "def", "https://static.tildacdn.com/tild6237-6265-4232-a233-663832313834/noroot.png"));
    List<Brand> brandsList = new ArrayList<>();
    List<Category> categoriesList = new ArrayList<>();
    List<Product> products = new ArrayList<>();

    // product features constants
    private final String[] featureTitles = {
            "Ultra Durability", "Smart Connectivity", "Eco-Friendly Design", "High-Speed Performance", "Compact Size", "User-Friendly Interface",
            "Advanced Security", "Versatile Compatibility", "Long Battery Life", "Elegant Aesthetic", "Crystal Clear Display", "Seamless Integration",
            "Multi-Functional Capability", "Lightweight Build", "Powerful Efficiency", "Enhanced Functionality", "Robust Construction", "Rapid Response Time",
            "Intuitive Controls", "Customizable Options"
    };
    private final String[] featureDescriptions = {
            "Built to withstand the toughest conditions, ensuring longevity.", "Connects effortlessly with all your smart devices.",
            "Made from sustainable materials, reducing environmental impact.", "Delivers lightning-fast processing speeds for all your tasks.",
            "Designed to fit easily in any bag or space.", "Simple navigation for users of all skill levels.",
            "State-of-the-art encryption to keep your data secure.", "Compatible with a wide range of products and platforms.",
            "Lasts up to 24 hours on a single charge, perfect for travel.", "Stylish design that complements any decor.",
            "Offers vibrant colors and sharp images for a stunning viewing experience.", "Easily integrates with your existing setup for a hassle-free experience.",
            "Offers multiple functions to meet diverse needs.", "Extremely portable, making it ideal for on-the-go use.",
            "Optimizes energy consumption without sacrificing performance.", "Goes beyond basic functionality with innovative features.",
            "Engineered to endure daily wear and tear.", "Responds to commands in a fraction of a second.",
            "Designed for effortless use with simple controls.", "Allows for personalized settings to suit your preferences."
    };
    private final String[] featureImages = new String[]{
            "https://images.pexels.com/photos/5702270/pexels-photo-5702270.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "https://images.pexels.com/photos/665214/pexels-photo-665214.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "https://images.pexels.com/photos/3722752/pexels-photo-3722752.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "https://images.pexels.com/photos/27679707/pexels-photo-27679707/free-photo-of-a-pair-of-headphones-sitting-on-top-of-a-keyboard.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "https://images.pexels.com/photos/16311111/pexels-photo-16311111/free-photo-of-close-up-of-computer-mouse.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "https://images.pexels.com/photos/2115256/pexels-photo-2115256.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "https://images.pexels.com/photos/14130157/pexels-photo-14130157.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "https://images.pexels.com/photos/19012035/pexels-photo-19012035/free-photo-of-acer-nitro-gaming-mouse-with-rgb-backlight.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "https://images.pexels.com/photos/5380602/pexels-photo-5380602.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "https://images.pexels.com/photos/3812048/pexels-photo-3812048.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
    };

    public void generateProducts() {
        long time = System.currentTimeMillis();

        Faker faker = new Faker();
        if (brandsList.size() <= 1) {
            addBrandAndCategory();
        }
        for (int i = 0; i < 1000; i++) {
            Product product = createProduct(faker);
            product.updateAvgRate();
            products.add(product);
        }
        productDAO.saveAll(products);
        products.clear();

        long time2 = System.currentTimeMillis();
        long duration = time2 - time;
        log.info("Products generated in {} ms", duration);

    }


    protected Product createProduct(Faker faker) {
        Product product = new Product();
        product.setName(faker.commerce().productName());
        product.setPrice(faker.number().numberBetween(50, 1000));
        product.setShortDescription((faker.lorem().characters(50, 100)));
        product.setCategory(categoriesList.get(faker.number().numberBetween(0, 6)));
        product.setImageUrl(setImageForCategory(product));
        product.setBrand(brandsList.get(faker.number().numberBetween(0, 6)));
        product.setCharacteristics(createCharacteristics(faker, product.getCategory().getName()));
        product.setReviews(generateReviews(faker, product));
        product.setFeatures(generateFeatures(faker, product));
        Inventory inventory = createInventory(faker, product);
        product.setInventory(inventory);
        return product;
    }

    private String setImageForCategory(Product product) {
        switch (product.getCategory().getName()) {
            case "Mice" -> {
                return images.get("Mouse");
            }
            case "Keyboards" -> {
                return images.get("Keyboard");
            }
            case "Joysticks and controllers" -> {
                return images.get("Joystick");
            }
            case "Headsets" -> {
                return images.get("Headset");
            }
            default -> {
                return images.get("def");
            }
        }
    }

    private Brand createBrand(int i) {
        Brand brand = new Brand();
        brand.setName(brands.get(i));
        return brand;
    }

    private Category createCategory(int i) {
        Category category = new Category();
        category.setName(categories.get(i));
        return category;

    }

    private void addBrandAndCategory() {
        for (int i = 0; i < brands.size(); i++) {
            Brand brand = createBrand(i);
            brandsList.add(brand);
            Category category = createCategory(i);
            categoriesList.add(category);
        }
        brandDAO.saveAll(brandsList);
        categoryDAO.saveAll(categoriesList);

    }

    private Map<String, String> createCharacteristics(Faker faker, String category) {
        Map<String, String> characteristics = new HashMap<>();

        switch (category) {
            case "Mice":
                characteristics.put("DPI", String.valueOf(faker.options().option(800, 1600, 2200, 600, 1200)));
                characteristics.put("Buttons", String.valueOf(faker.number().numberBetween(2, 8)));
                characteristics.put("Interface", faker.options().option("USB", "Bluetooth", "2,4 GHz"));
                characteristics.put("Color", faker.options().option("Red", "White", "Black", "Blue", "Yellow"));
                break;
            case "Keyboards":
                characteristics.put("Layout", faker.options().option("QWERTY", "AZERTY", "QWERTZ"));
                characteristics.put("Type", faker.options().option("Hybrid mechanical-membrane", "Optical-mechanical", "Scissors", "Mechanical", "Membrane"));
                characteristics.put("Interface", faker.options().option("USB", "Bluetooth", "2,4 GHz"));
                characteristics.put("Size", faker.options().option("100%", "75%", "60%"));
                break;
            case "Headsets":
                characteristics.put("Type", faker.options().option("In-Ear", "On-Ear", "Over-Ear"));
                characteristics.put("Connection type", faker.options().option("Wired", "Wireless", "Combined"));
                characteristics.put("Noise Cancelling", faker.options().option("With noise cancelling", "Without noise cancelling"));
                break;
        }

        return characteristics;
    }

    private Inventory createInventory(Faker faker, Product product) {
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setQuantity(faker.number().numberBetween(0, 100));
        return inventory;
    }

    public List<Review> generateReviews(Faker faker, Product product) {
        List<User> users = userRepository.findAll();
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Review review = new Review();
            review.setComment(REVIEW_TEXT);
            review.setRate(faker.random().nextInt(3, 5));
            review.setUser(users.get(faker.random().nextInt(1, users.size() - 1)));
            reviews.add(review);
            review.setProduct(product);
        }
        return reviews;
    }

    public List<Feature> generateFeatures(Faker faker, Product product) {
        List<Feature> features = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Feature feature = new Feature();
            feature.setTitle(featureTitles[faker.random().nextInt(0, featureTitles.length - 1)]);
            feature.setDescription(featureDescriptions[faker.random().nextInt(0, featureDescriptions.length - 1)]);
            feature.setImageUrl(featureImages[faker.random().nextInt(0, featureImages.length - 1)]);
            feature.setProduct(product);
            features.add(feature);
        }
        return features;
    }
}
