package MeshX.HypeLink.head_office.item.common;

import MeshX.HypeLink.head_office.item.model.entity.Category;
import MeshX.HypeLink.head_office.item.model.entity.Color;
import MeshX.HypeLink.head_office.item.model.entity.Size;
import MeshX.HypeLink.head_office.item.repository.CategoryRepository;
import MeshX.HypeLink.head_office.item.repository.ColorRepository;
import MeshX.HypeLink.head_office.item.repository.SizeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitialize {
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

    @PostConstruct
    @Transactional
    public void init() {
        initCategories();
        initColors();
        initSizes();
    }

    private void initCategories() {
        if (categoryRepository.count() > 0) {
            log.info("✅ Category already initialized");
            return;
        }

        List<Category> categories = List.of(
                createCategory("상의"),
                createCategory("하의"),
                createCategory("아우터"),
                createCategory("신발"),
                createCategory("가방"),
                createCategory("액세서리")
        );

        categoryRepository.saveAll(categories);
        log.info("✅ Default Categories inserted successfully");
    }

    private void initColors() {
        if (colorRepository.count() > 0) {
            log.info("✅ Color already initialized");
            return;
        }

        List<Color> colors = List.of(
                createColor("블랙", "#000000"),
                createColor("화이트", "#FFFFFF"),
                createColor("그레이", "#808080"),
                createColor("레드", "#FF0000"),
                createColor("블루", "#0000FF"),
                createColor("그린", "#00FF00")
        );

        colorRepository.saveAll(colors);
        log.info("✅ Default Colors inserted successfully");
    }

    private void initSizes() {
        if (sizeRepository.count() > 0) {
            log.info("✅ Size already initialized");
            return;
        }

        List<Size> sizes = List.of(
                createSize("XS"),
                createSize("S"),
                createSize("M"),
                createSize("L"),
                createSize("XL"),
                createSize("XXL")
        );

        sizeRepository.saveAll(sizes);
        log.info("✅ Default Sizes inserted successfully");
    }

    private Category createCategory(String name) {
        return Category.builder()
                .category(name)
                .build();
    }

    private Color createColor(String name, String code) {
        return Color.builder()
                .colorName(name)
                .colorCode(code)
                .build();
    }

    private Size createSize(String size) {
        return Size.builder()
                .size(size)
                .build();
    }
}
