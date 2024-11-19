package project.controller.user;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.business.OrderBusiness;
import project.payload.request.PaginateRequest;
import project.payload.request.user.OrderCreateRequest;
import project.payload.request.user.OrderRequest;
import project.payload.request.user.OrderUpdateRequest;
import project.payload.response.ResponseObject;
import project.resource.OrderResource;
import project.resource.PostResource;
import project.service.OrderService;
import project.util.PagingUtil;

@RestController
@RequestMapping("/api/v1/user/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderBusiness orderBusiness;

    @GetMapping
    public ResponseEntity<ResponseObject> index(OrderRequest orderRequest, PaginateRequest paginateRequest){
        Sort sort = Sort.by(paginateRequest.getSort(), paginateRequest.getColumn());
        PageRequest pageRequest = PagingUtil.getPageRequest(paginateRequest.getPage(), paginateRequest.getLimit(), sort);

        Page<OrderResource> orderResources = orderBusiness.getOrderResources(orderRequest, pageRequest);
        ResponseObject.Meta meta = new ResponseObject.Meta();
        meta.setPage(pageRequest.getPageNumber());
        meta.setTotal(orderResources.getTotalElements());
        meta.setMessage("Ok");

        ResponseObject result = new ResponseObject(orderResources.getContent(), meta);
        return ResponseEntity.ok(new ResponseObject(result));
    }
    @GetMapping({"/{id}","/{id}/"})
    public ResponseEntity<ResponseObject> show(@PathVariable String id){
        OrderResource orderResource=orderBusiness.getById(id);
        return ResponseEntity.ok(new ResponseObject(orderResource));
    }
    @PostMapping({"/create", "/create/"})
    public ResponseEntity<ResponseObject> store(@RequestBody @Valid OrderCreateRequest request) {
        OrderResource orderResource= orderBusiness.create(request);
        return ResponseEntity.ok(ResponseObject.ok("Ok"));
    }

    @PutMapping({"/update", "/update/"})
    public ResponseEntity<ResponseObject> update(@RequestBody @Valid OrderUpdateRequest request) {
        return ResponseEntity.ok(ResponseObject.ok(orderBusiness.update(request)));
    }


    @DeleteMapping({"/delete/{id}", "/delete/{id}/"})
    public ResponseEntity<ResponseObject> delete(@PathVariable String id) {
        orderBusiness.deleteById(id);
        return ResponseEntity.ok(ResponseObject.ok("Ok"));
    }

}
