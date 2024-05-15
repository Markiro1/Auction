package com.ashapiro.auction.repository;

import com.ashapiro.auction.dto.bid.BidDetailsDto;
import com.ashapiro.auction.dto.bid.BidInfoDto;
import com.ashapiro.auction.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {

    Long countBidByAuctionId(Long auctionId);

    @Query("select new com.ashapiro.auction.dto.bid.BidDetailsDto(" +
            "b.id," +
            "b.sum as bid_sum," +
            "b.bidTime," +
            "b.auction.id," +
            "p.name as product_name," +
            "b.customer.id," +
            "u.email as customer_email" +
            ") from Bid b " +
            "join Auction a on b.auction.id = a.id " +
            "join Product p on a.product.id = p.id " +
            "join User u on b.customer.id = u.id")
    List<BidDetailsDto> getAllBidDetails();

    Optional<BidInfoDto> findBidById(Long bidId);

    @Modifying
    @Query("delete from Bid where id not in (select id from Bid order by id desc limit 10)")
    int clearBids();
}
