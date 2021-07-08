package com.exam.shop.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString(of = {"id", "categoryName", "parent"})
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

    @OneToMany(mappedBy = "parent",cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Category> children = new ArrayList<>();

    @OneToOne(mappedBy = "category")
    private Item item;

    //category 부모 생성 메소드.
    public static Category createParent(String categoryName) {
        Category newParent = new Category();
        newParent.setCategoryName(categoryName);
        return newParent;
    }

    //category 자식 생성 메소드.
    public static Category createChildren(String categoryName, Category newParent) {
        Category newChild = new Category();
        newChild.setCategoryName(categoryName);
        newParent.addCategoryChildren(newChild);
        return newChild;
    }

    //부모자식 연관 관계 편의 메소드.
    public void addCategoryChildren(Category child) {
        this.children.add(child);
        child.setParent(this);
    }

}
