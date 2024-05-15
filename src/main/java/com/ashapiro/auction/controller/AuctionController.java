package com.ashapiro.auction.controller;

import com.ashapiro.auction.dto.auction.*;
import com.ashapiro.auction.dto.bid.BidDto;
import com.ashapiro.auction.dto.product.SimpleProductDto;
import com.ashapiro.auction.entity.statuses.AuctionStatus;
import com.ashapiro.auction.service.AuctionService;
import com.ashapiro.auction.service.BidService;
import com.ashapiro.auction.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/auction")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;
    private final ProductService productService;
    private final BidService bidService;

    @GetMapping("/admin/add")
    public String addAuction(Model model) {
        Map<String, List<SimpleProductDto>> productNamesMap = productService.getEmailsAndProductNames();
        model.addAttribute("auctionForm", new AuctionRequestDto());
        model.addAttribute("productNamesMap", productNamesMap);
        return "auction/addAuction";
    }

    @PostMapping("/admin/add")
    public String addAuction(AuctionRequestDto auctionDto, RedirectAttributes redirectAttributes) {
        String message = auctionService.add(auctionDto);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/logo/admin";
    }

    @GetMapping("/admin/all")
    public String getAll(@ModelAttribute("message") String message, Model model) {
        List<AuctionDataDto> auctions = auctionService.getAll();
        AuctionStatus.Status[] statuses = AuctionStatus.Status.values();
        model.addAttribute("auctions", auctions);
        model.addAttribute("statuses", statuses);
        if (message != null && !message.isEmpty()) {
            model.addAttribute("message", message);
        }
        return "auction/allAuctions";
    }

    @PatchMapping("/admin/status/{auctionId}")
    public String updateAuctionStatus(@PathVariable Long auctionId, @RequestParam("status") String status,
                                      RedirectAttributes redirectAttributes) {
        String message = auctionService.updateStatus(auctionId, status);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/auction/admin/all";
    }

    @GetMapping("/admin/info/{auctionId}")
    public String getAuctionInfo(@PathVariable Long auctionId, Model model) {
        AuctionDto auction = auctionService.getAuction(auctionId);
        model.addAttribute("auction", auction);
        return "auction/infoAuction";
    }

    @GetMapping("/admin/delete/{auctionId}")
    public String deleteAuction(@PathVariable Long auctionId, RedirectAttributes redirectAttributes) {
        String message = auctionService.deleteAuction(auctionId);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/auction/admin/all";
    }

    @GetMapping("/user/won-auctions/{userId}")
    public String allWonAuctions(@PathVariable Long userId, Model model) {
        List<SimpleWonAuctionDto> wonAuctionDtos = auctionService.getWonAuctions(userId);
        model.addAttribute("wonAuctions", wonAuctionDtos);
        return "auction/wonAuctions";
    }

    @GetMapping("/user/won-auction/{auctionId}")
    public String getWonAuction(@PathVariable Long auctionId, Model model) {
        WonAuctionDto wonAuctionDto = auctionService.getWonAuctionById(auctionId);
        String paymentStatus = auctionService.getWonAuctionPaymentStatus(auctionId);
        model.addAttribute("paymentStatus", paymentStatus);
        model.addAttribute("wonAuction", wonAuctionDto);
        return "auction/wonAuction";
    }

    @GetMapping("/user/all/active")
    public String getAllInProgress(@PageableDefault(size = 4) Pageable pageable, Model model) {
        Page<SimpleAuctionDto> activeAuctions =  auctionService.getActiveAuctions(pageable);
        int totalPages = activeAuctions.getTotalPages();

        model.addAttribute("activeAuctions", activeAuctions);
        model.addAttribute("totalPages", totalPages);
        return "auction/activeAuctions";
    }

    @GetMapping("/user/{auctionId}")
    public String getAuctionDetailsById(@PathVariable Long auctionId, Model model) {
        AuctionDetailsDto auction = auctionService.getAuctionDetailsById(auctionId);
        Long countOfBids = bidService.getCountOfBidsByAuctionId(auctionId);
        BigDecimal currentPrice = auctionService.getMaxCurrentPrice(auctionId);
        String serverIp = System.getenv("IP_ADDRESS");

        model.addAttribute("serverIp", serverIp);
        model.addAttribute("currentPrice", currentPrice);
        model.addAttribute("countOfBids", countOfBids);
        model.addAttribute("auction", auction);
        model.addAttribute("auctionId", auctionId);
        model.addAttribute("bid", new BidDto());
        return "auction/auction";
    }
}
