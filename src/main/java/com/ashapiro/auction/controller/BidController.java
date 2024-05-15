package com.ashapiro.auction.controller;

import com.ashapiro.auction.dto.bid.BidDetailsDto;
import com.ashapiro.auction.dto.bid.BidDto;
import com.ashapiro.auction.dto.bid.BidInfoDto;
import com.ashapiro.auction.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;


    @GetMapping("/user/count/{auctionId}")
    public Long getCountOfBidsByAuctionId(@PathVariable Long auctionId) {
        return bidService.getCountOfBidsByAuctionId(auctionId);
    }

    @PostMapping("/user/add")
    @ResponseBody
    public void addBid(BidDto bidDto) {
        bidService.save(bidDto);
    }

    @GetMapping("/admin/all")
    public String getAll(@ModelAttribute("message") String message, Model model) {
        List<BidDetailsDto> allBids = bidService.getAllBidDetails();
        model.addAttribute("bids", allBids);
        if (message != null && !message.isEmpty()) {
            model.addAttribute("message", message);
        }
        return "bid/allBids";
    }

    @GetMapping("/admin/bid/{bidId}")
    public String getBidInfo(@PathVariable Long bidId, Model model) {
        BidInfoDto bid = bidService.getBidInfo(bidId);
        model.addAttribute("bid", bid);
        return "bid/infoBid";
    }

    @DeleteMapping("/admin/bid/delete/{bidId}")
    public String deleteBid(@PathVariable Long bidId, RedirectAttributes redirectAttributes) {
        String message = bidService.deleteBid(bidId);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/bids/admin/all";
    }
}
