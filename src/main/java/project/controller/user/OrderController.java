package project.controller.user;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.business.OrderBusiness;
import project.payload.request.PaginateRequest;
import project.payload.request.user.OrderCreateRequest;
import project.payload.request.user.OrderRequest;
import project.payload.request.user.OrderUpdateRequest;
import project.payload.response.ResponseObject;
import project.resource.OrderResource;
import project.resource.ShowTopResource;
import project.util.PagingUtil;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderBusiness orderBusiness;

    @GetMapping
    public ResponseEntity<ResponseObject> index(OrderRequest orderRequest, PaginateRequest paginateRequest) {
        Sort sort = Sort.by(paginateRequest.getSort(), paginateRequest.getColumn());
        PageRequest pageRequest = PagingUtil.getPageRequest(paginateRequest.getPage(), paginateRequest.getLimit(), sort);
        Page<OrderResource> orderResources = orderBusiness.getOrderResources(orderRequest, pageRequest);
        Map<String, Object> meta = PagingUtil.createMeta(orderResources);
        meta.put("summary", orderBusiness.getSummary(orderRequest));
        ResponseObject result = new ResponseObject(orderResources.getContent(), meta);
        return ResponseEntity.ok(result);
    }


    @GetMapping({"/scheduled/{id}", "/scheduled/{id}"})
    public ResponseEntity<ResponseObject> getScheduledOfArtist(@PathVariable String id) {
        List<OrderResource> orderResources = orderBusiness.getScheduledOfArtist(id);
        return ResponseEntity.ok(new ResponseObject(orderResources));
    }



    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity<ResponseObject> show(@PathVariable String id) {
        OrderResource orderResource = orderBusiness.getById(id);
        return ResponseEntity.ok(new ResponseObject(orderResource));
    }

    @PostMapping({"/create", "/create/"})
    public ResponseEntity<ResponseObject> store(@RequestBody @Valid OrderCreateRequest request) {

        if (!orderBusiness.checkAvailable(request)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseObject.error("Order cannot be created: request is invalid or resources are unavailable.", HttpStatus.BAD_REQUEST));
        }
        OrderResource orderResource = orderBusiness.create(request);
        return ResponseEntity.ok(ResponseObject.ok(orderResource));
    }

    @PutMapping({"/update", "/update/"})
    public ResponseEntity<ResponseObject> update(@RequestBody @Valid OrderUpdateRequest request) {
        if (!orderBusiness.checkAvailable(request)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseObject.error("Order cannot be created: request is invalid or resources are unavailable.", HttpStatus.BAD_REQUEST));
        }
        return ResponseEntity.ok(ResponseObject.ok(orderBusiness.update(request)));
    }

    @PutMapping({"/update-status", "/update-status/"})
    public ResponseEntity<ResponseObject> updateStatus(@RequestParam String id, @RequestParam String status,  @RequestParam(required = false)String reason) {
        if (!orderBusiness.checkAvailable(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseObject.error("Order cannot be created: request is invalid or resources are unavailable.", HttpStatus.BAD_REQUEST));
        }
        return ResponseEntity.ok(ResponseObject.ok(orderBusiness.updateStatus(id, status, reason)));
    }


    @DeleteMapping({"/delete/{id}", "/delete/{id}/"})
    public ResponseEntity<ResponseObject> delete(@PathVariable String id) {
        orderBusiness.deleteById(id);
        return ResponseEntity.ok(ResponseObject.ok("Ok"));
    }

    @GetMapping({"/show-top", "/show-top/"})
    public ResponseEntity<ResponseObject> showTop(OrderRequest request) {
        List<ShowTopResource> result = orderBusiness.getShowTopReport(request);
        return new ResponseEntity<>(new ResponseObject(result), HttpStatus.OK);
    }

}
