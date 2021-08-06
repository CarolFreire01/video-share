//package br.com.carol.videoshare.Resource;
//
//import br.com.carol.videoshare.dto.CategoryDto;
//import br.com.carol.videoshare.dto.VideoDto;
//import br.com.carol.videoshare.entities.Category;
//import br.com.carol.videoshare.entities.Video;
//import br.com.carol.videoshare.service.impl.CategoryServiceImpl;
//import br.com.carol.videoshare.service.impl.VideoServiceImpl;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.*;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class CategoryResourceTest {
//
//    @Test
//    public void getAllVideosTest_Success(){
//        List<Category> categories = new ArrayList<>();
//
//        Category category1 = new Category(Long.parseLong("1"), "Video 1", "verde",
//                Collections.singleton(new Video(Long.parseLong("1"), "Teste 1", "Video api teste", "www.teste.com", new Category())));
//        Category category2 = new Category(Long.parseLong("2"), "Teste 2", "amarelo",
//                Collections.singleton(new Video(Long.parseLong("1"), "Teste 1", "Video api teste", "www.teste.com", new Category())));
//
//        categories.add(category1);
//        categories.add(category2);
//
//        CategoryServiceImpl service = Mockito.mock(CategoryServiceImpl.class);
//        when(service.findAllCategory()).thenReturn(categories);
//
//        List<Category> expected = service.findAllCategory();
//
//        assertEquals(expected, categories);
//    }
//
//    /*
//    Implements test for FindVideosByCategory
//     */
//
//    @Test
//    public void getVideoByID_Success(){
//
//        Optional<Category> category = Optional.of(new Category(Long.parseLong("1"), "Video 1", "verde",
//                Collections.singleton(new Video(Long.parseLong("1"), "Teste 1", "Video api teste", "www.teste.com", new Category()))));
//
//
//        CategoryServiceImpl service = Mockito.mock(CategoryServiceImpl.class);
//        when(service.findCategoryById(1L)).thenReturn(category);
//
//        Optional<Category> expected = service.findCategoryById(1L);
//
//        assertEquals(expected, category);
//    }
//
//    @Test
//    public void deleteVideo_Success(){
//
//        Optional<Category> category = Optional.of(new Category(Long.parseLong("1"), "Video 1", "verde",
//                Collections.singleton(new Video(Long.parseLong("1"), "Teste 1", "Video api teste", "www.teste.com", new Category()))));
//
//        CategoryServiceImpl service = Mockito.mock(CategoryServiceImpl.class);
//        when(service.findCategoryById(1L)).thenReturn(category);
//
//        service.deleteCategory(1L);
//    }
//
//    @Test
//    public void saveVideo_Success() {
//
//        CategoryDto categoryDto = new CategoryDto(Long.parseLong("1"), "Video 1", "verde");
//
//        CategoryServiceImpl service = Mockito.mock(CategoryServiceImpl.class);
//        when(service.addCategory(ArgumentMatchers.any(CategoryDto.class))).thenReturn(categoryDto);
//
//        CategoryDto expected = service.addCategory(categoryDto);
//
//        assertEquals(expected, categoryDto);
//    }
//
//    @Test
//    public void updateVideo_Success() {
//
//        CategoryDto categoryDto = new CategoryDto(1L, "PHP", "vermelho");
//
//        CategoryDto categoryDtoNew = new CategoryDto(1L, "Java", "azul");
//
//        CategoryServiceImpl service = Mockito.mock(CategoryServiceImpl.class);
//        when(service.findCategoryById(categoryDto.getId())).thenReturn(Optional.of(categoryDtoNew));
//
//        service.updateCategory(categoryDtoNew, categoryDto.getId());
//
//    }
//}
