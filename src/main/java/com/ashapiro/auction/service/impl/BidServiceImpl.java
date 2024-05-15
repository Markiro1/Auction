package com.ashapiro.auction.service.impl;

import com.ashapiro.auction.dto.auction.AuctionUpdateDto;
import com.ashapiro.auction.dto.bid.BidDetailsDto;
import com.ashapiro.auction.dto.bid.BidDto;
import com.ashapiro.auction.dto.bid.BidInfoDto;
import com.ashapiro.auction.entity.Auction;
import com.ashapiro.auction.entity.Bid;
import com.ashapiro.auction.entity.User;
import com.ashapiro.auction.exceptions.user.UserNotFoundException;
import com.ashapiro.auction.exceptions.auction.AuctionNotFoundException;
import com.ashapiro.auction.exceptions.bid.BidNotFoundException;
import com.ashapiro.auction.repository.BidRepository;
import com.ashapiro.auction.service.AuctionService;
import com.ashapiro.auction.service.BidService;
import com.ashapiro.auction.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final AuctionService auctionService;
    private final UserService userService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public Long getCountOfBidsByAuctionId(Long auctionId) {
        return bidRepository.countBidByAuctionId(auctionId);
    }

    @Transactional
    @Override
    public void save(BidDto bidDto) {
        Long customerId = bidDto.getCustomerId();
        Long auctionId = bidDto.getAuctionId();

        User customer = userService.getById(customerId)
                .orElseThrow(() -> new UserNotFoundException(customerId));
        Auction auction = auctionService.getById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException(auctionId));

        Bid bid = new Bid();
        bid.setSum(bidDto.getSum());
        bid.addAuction(auction);
        bid.addCustomer(customer);

        bidRepository.save(bid);

        auction.setCurrentPrice(auction.getCurrentPrice().add(bidDto.getSum()));
        sendUpdateAuctionDataToAllConnectedUsers(auctionId);
    }

    @Override
    public List<BidDetailsDto> getAllBidDetails() {
        return bidRepository.getAllBidDetails();
    }

    @Override
    public BidInfoDto getBidInfo(Long bidId) {
        return bidRepository.findBidById(bidId)
                .orElseThrow(() -> new BidNotFoundException(bidId));
    }

    @Override
    @Transactional
    public String deleteBid(Long bidId) {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new BidNotFoundException(bidId));
        Auction auction = bid.getAuction();
        Long auctionId = auction.getId();
        auction.setCurrentPrice(auction.getCurrentPrice().subtract(bid.getSum()));
        try {
            bidRepository.delete(bid);
            sendUpdateAuctionDataToAllConnectedUsers(auctionId);
            return "Bid deleted successfully";
        } catch (Exception e) {
            return String.format("Error deleting bid with id: %d", bidId);
        }
    }

    @Override
    @Transactional
    public void clearBids() {
        int deletedRows = bidRepository.clearBids();
        if (deletedRows > 1) {
            log.info("Successfully deleted bids.");
        } else {
            log.info("No bids were deleted.");
        }
    }

    private void sendUpdateAuctionDataToAllConnectedUsers(Long auctionId) {
        Long countOfBids = getCountOfBidsByAuctionId(auctionId);
        BigDecimal currentPrice = auctionService.getMaxCurrentPrice(auctionId);
        AuctionUpdateDto auctionUpdateDto = new AuctionUpdateDto(countOfBids, currentPrice);
        simpMessagingTemplate.convertAndSend("/topic/addBid", auctionUpdateDto);
    }
}
