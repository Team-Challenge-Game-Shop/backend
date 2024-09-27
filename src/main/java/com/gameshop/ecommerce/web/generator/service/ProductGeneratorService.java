package com.gameshop.ecommerce.web.generator.service;

import com.gameshop.ecommerce.web.product.dao.BrandDAO;
import com.gameshop.ecommerce.web.product.dao.CategoryDAO;
import com.gameshop.ecommerce.web.product.dao.ProductDAO;
import com.gameshop.ecommerce.web.product.model.*;
import com.gameshop.ecommerce.web.review.model.Review;
import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    private final List<String> categories = List.of("Keyboards", "Mice", "Headsets", "Mouse mats", "Joysticks and controllers", "Gaming chairs");
    private final List<String> brands = List.of("Logitech", "Razer", "Acer", "Asus", "Gigabyte", "MSI");
    private final Map<String, String> images = new HashMap<>(Map.of("Mouse", "https://girlsonlytravel.com/img/works/mouse.png",
            "Keyboard", "https://girlsonlytravel.com/img/works/keyboard.png", "Headset",
            "https://girlsonlytravel.com/img/works/headset.png", "Joystick",
            "https://girlsonlytravel.com/img/works/joystick.png",
            "def", "https://static.tildacdn.com/tild6237-6265-4232-a233-663832313834/noroot.png"));

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

    private final Map<String, List<String>> productImages = Map.of(
            "Mice", List.of(
                    "https://i.ibb.co/4RkkWFW/onder-ortel-i4-Wv-ZELUIl-Q-unsplash.jpg",
                    "https://i.ibb.co/DVY7fPt/mike-meyers-iqc-EMi-Mi-RRI-unsplash.jpg",
                    "https://i.ibb.co/pLBsfm5/pascal-m-4-Pch-FKr-Uw84-unsplash.jpg",
                    "https://i.ibb.co/L81hzpD/andrey-matveev-TAs-JIiy-S-n-M-unsplash.jpg",
                    "https://i.ibb.co/7jdTTn9/shagal-sajid-Ox7m-Smsljkc-unsplash.jpg",
                    "https://i.ibb.co/M51Wf1F/oscar-ivan-esquivel-arteaga-Ztx-ED1cp-B1-E-unsplash.jpg",
                    "https://i.ibb.co/n05n4hK/frankie-Vghb-BAYq-UJ0-unsplash.jpg",
                    "https://i.ibb.co/mGXz9t4/guillaume-issaly-s7-Sg-BGz-Ng-Io-unsplash.jpg",
                    "https://i.ibb.co/ThKj5Vp/auguras-pipiras-w-BGV52-CGY5w-unsplash.jpg",
                    "https://i.ibb.co/0Qz1P8v/emiliano-cicero-xo-MJALGPr-I-unsplash.jpg",
                    "https://i.ibb.co/RH98s70/florian-berger-p-IOz0kdw-Zs-M-unsplash.jpg"),
            "Keyboards", List.of(
                    "https://i.ibb.co/K07d1y4/bryan-natanael-h-R8l1s4u8-QE-unsplash.jpg",
                    "https://i.ibb.co/0nTHSqT/bryan-natanael-V-Zg-V8-Uu-L3-M-unsplash.jpg",
                    "https://i.ibb.co/xzyt1y2/islam-wazery-Bcto3-Tr-ALq4-unsplash.jpg",
                    "https://i.ibb.co/kXcZMkC/sebastian-banasiewcz-CMs6-ZGOdyho-unsplash.jpg",
                    "https://i.ibb.co/1fGXh34/jan-loyde-cabrera-6dds-NS2dybg-unsplash.jpg",
                    "https://i.ibb.co/b1LS4kB/jan-loyde-cabrera-o-YUO3-KAJky0-unsplash.jpg",
                    "https://i.ibb.co/JHMTYGP/jan-loyde-cabrera-a343i3e-Hawk-unsplash.jpg",
                    "https://i.ibb.co/n6HtS3q/mahrous-houses-DN4-VHSNZg-YI-unsplash.jpg",
                    "https://i.ibb.co/vstcDPp/ikhsan-hidayat-l-L-x-P4-CEXa-Y-unsplash.jpg",
                    "https://i.ibb.co/9TCrbkk/wesley-tingey-FNhyekndn-SM-unsplash.jpg"),
            "Headsets", List.of(
                    "https://i.ibb.co/fnHrZ0M/kiran-ck-7-KMh-Zqylgss-unsplash.jpg",
                    "https://i.ibb.co/wYxhkpz/daniel-gaffey-A5g-UDqd6-w-unsplash.jpg",
                    "https://i.ibb.co/sCbQ6LW/wu-yi-s-Yhy-E3-Ce-Ty4-unsplash.jpg",
                    "https://i.ibb.co/BqmbLFz/kiran-ck-c-Dr-Iiipt-Fq-E-unsplash.jpg",
                    "https://i.ibb.co/6DttFXj/ian-powell-IZq4-N-Ob-Nv4-unsplash.jpg",
                    "https://i.ibb.co/dtTPQ7K/kiran-ck-LSNJ-pltdu8-unsplash.jpg",
                    "https://i.ibb.co/D4xjNxk/rebekah-yip-c2-NALe-X2w-Nk-unsplash.jpg",
                    "https://i.ibb.co/z4063QX/thamara-maura-o-VLY3-CP8g-unsplash.jpg",
                    "https://i.ibb.co/Fw0DMwn/barry-a-6l-V7-ba-JTtw-unsplash.jpg",
                    "https://i.ibb.co/42JHYDV/andrey-matveev-hu-F-od-Qnp-Mo-unsplash.jpg",
                    "https://i.ibb.co/HYkX10J/li-zhang-c90-RNEla-BDs-unsplash.jpg"),
            "Joysticks and controllers", List.of(
                    "https://i.ibb.co/7jGR0dF/igor-karimov-AGf-KACMHJ8-unsplash.jpg",
                    "https://i.ibb.co/GkByBg7/alberto-bianchini-Dze-DCh-Tb-D3-U-unsplash.jpg",
                    "https://i.ibb.co/FqHTJcg/muhammad-toqeer-NRJ9ipvz2i-E-unsplash.jpg",
                    "https://i.ibb.co/G30GttV/igor-karimov-MV4-G-Qf-FVXM-unsplash.jpg",
                    "https://i.ibb.co/wQX0vyY/igor-karimov-GOIr-ALk-IZY4-unsplash.jpg",
                    "https://i.ibb.co/2kSv3Md/vignesh-jayaprakash-NL3-HAIq2a-RE-unsplash.jpg",
                    "https://i.ibb.co/tznk5z0/kevin-wang-q-OCn-QXxw-Wi-Q-unsplash.jpg",
                    "https://i.ibb.co/Dk7WtTB/berke-can-CPL6-YRO62-A-unsplash.jpg",
                    "https://i.ibb.co/T1b0YWB/sara-kurfess-jqp-RECmi-NEU-unsplash.jpg",
                    "https://i.ibb.co/9Hqf9SJ/arun-prakash-Am-GGGOMSTAM-unsplash.jpg",
                    "https://i.ibb.co/Kh1Dhzs/ravi-palwe-Vrw58w-U4a-I-unsplash.jpg",
                    "https://i.ibb.co/DC7kHV2/techrpt-E8-Gf-AQw-GXj8-unsplash.jpg"));

    private final ProductDAO productDAO;
    private final BrandDAO brandDAO;
    private final CategoryDAO categoryDAO;
    private final UserRepository userRepository;

    List<Product> products = new ArrayList<>();

    @Transactional
    public void generateProducts(int amount) {
        long time = System.currentTimeMillis();

        Faker faker = new Faker();

        final var brandsList = getOrCreateBrands();
        final var categoriesList = getOrCreateCategories();

        for (int i = 0; i < amount; i++) {
            Product product = createProduct(faker, brandsList, categoriesList);
            product.updateAvgRate();
            products.add(product);
        }
        productDAO.saveAll(products);
        products.clear();

        long time2 = System.currentTimeMillis();
        long duration = time2 - time;
        log.info("Products generated in {} ms", duration);

    }

    private List<Category> getOrCreateCategories() {
        final var savedCategories = categoryDAO.findAll();

        if (!savedCategories.isEmpty()) {
            return savedCategories;
        }

        final var categoriesList = new ArrayList<Category>();
        for (String categoryName : categories) {
            categoriesList.add(
                    categoryDAO.save(
                            new Category(null, categoryName)));
        }
        return categoriesList;
    }

    private List<Brand> getOrCreateBrands() {
        final var savedBrands = brandDAO.findAll();
        if (!savedBrands.isEmpty()) {
            return savedBrands;
        }

        final var brandList = new ArrayList<Brand>();
        for (String brandName : brands) {
            brandList.add(
                    brandDAO.save(
                            new Brand(null, brandName)));
        }
        return brandList;
    }


    protected Product createProduct(Faker faker, List<Brand> brandsList, List<Category> categoriesList) {
        Product product = new Product();
        product.setName(faker.commerce().productName());
        product.setPrice(faker.number().numberBetween(50, 1000));
        product.setIsSale(faker.random().nextBoolean());
        if (product.getIsSale()) {
            product.setPriceWithSale((int) (product.getPrice() * 0.75));
        }
        product.setShortDescription(faker.lorem().words(10).stream().reduce("", (s1, s2) -> s1 + " " + s2));
        product.setLongDescription(faker.lorem().words(30).stream().reduce("", (s1, s2) -> s1 + " " + s2));
        product.setCategory(categoriesList.get(faker.number().numberBetween(0, categoriesList.size())));
        product.setImageUrl(setImageForCategory(product));
        product.setImages(generateImages(faker, product));
        product.setBrand(brandsList.get(faker.number().numberBetween(0, brandsList.size())));
        product.setCharacteristics(createCharacteristics(faker, product.getCategory().getName()));
        product.setReviews(generateReviews(faker, product));
        product.setFeatures(generateFeatures(faker, product));
        Inventory inventory = createInventory(faker, product);
        product.setInventory(inventory);
        product.setIsPresent(inventory.getQuantity() > 0);
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

    private List<ProductImage> generateImages(Faker faker, Product product) {
        final var imageUrls = productImages.getOrDefault(product.getCategory().getName(),
                List.of("https://static.tildacdn.com/tild6237-6265-4232-a233-663832313834/noroot.png"));

        final var result = new ArrayList<ProductImage>();
        for (int i = 0; i < 5; i++) {
            final var index = faker.number().numberBetween(0, imageUrls.size());
            final var productImage = new ProductImage();
            productImage.setProduct(product);
            productImage.setUrl(imageUrls.get(index));
            productImage.setCreatedAt(Instant.now());
            result.add(productImage);
        }

        return result;
    }
}
