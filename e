[1mdiff --git a/src/model/Airport.java b/src/model/Airport.java[m
[1mindex 193d7d4..5f6fd95 100644[m
[1m--- a/src/model/Airport.java[m
[1m+++ b/src/model/Airport.java[m
[36m@@ -129,6 +129,7 @@[m [mpublic class Airport {[m
 		orderType = ORDERED_BY_DESTINATION_CITY;[m
 	}[m
 [m
[32m+[m	[32m//Arrays.binarySearch[m
 	/**[m
 	 * [m
 	 * @param date[m
[36m@@ -158,6 +159,7 @@[m [mpublic class Airport {[m
 		return flight;[m
 	}[m
 [m
[32m+[m	[32m//my binary search[m
 	/**[m
 	 * [m
 	 * @param hour[m
[36m@@ -165,19 +167,31 @@[m [mpublic class Airport {[m
 	public Flight searchByTime(double hour) {[m
 		Flight flight = null;[m
 		TimeComparator tc = new TimeComparator(); [m
[32m+[m		[32mFlight key = new Flight(new Date(1, 1, 1, hour), "", 0, "", 0);[m
 		if(orderType == ORDERED_BY_TIME) {[m
 			int low = 0;[m
[31m-			int hight = flights.length-1;[m
[31m-			int mid = (low+hight)/2;[m
[31m-			//while() {[m
[31m-				//TODO implementar todo[m
[31m-			//}[m
[32m+[m			[32mint high = flights.length-1;[m
[32m+[m			[32mint mid = (low+high)/2;[m
[32m+[m			[32mwhile(low <= high && flight == null) {[m
[32m+[m				[32mif(tc.compare(key, flights[mid]) < 0) {[m
[32m+[m					[32mhigh = mid-1;[m
[32m+[m				[32m}[m
[32m+[m				[32melse if(tc.compare(key, flights[mid]) > 0) {[m
[32m+[m					[32mlow = mid+1;[m
[32m+[m				[32m}[m
[32m+[m				[32melse {[m
[32m+[m					[32mflight = flights[mid];[m
[32m+[m				[32m}[m
[32m+[m			[32m}[m
 		}[m
 		else {[m
[31m-			for(int i = 0; i < flights.length; i++) {[m
[31m-				[m
[32m+[m			[32mfor(int i = 0; i < flights.length && flight == null; i++) {[m
[32m+[m				[32mif(tc.compare(key, flights[i]) == 0) {[m
[32m+[m					[32mflight = flights[i];[m
[32m+[m				[32m}[m
 			}[m
 		}[m
[32m+[m		[32mreturn flight;[m
 	}[m
 [m
 	/**[m
[36m@@ -187,12 +201,26 @@[m [mpublic class Airport {[m
 	public Flight searchByAirline(String airline) {[m
 		Flight flight = null;[m
 		Flight key = new Flight(new Date(1,1,1,1), airline, 0, "", 0);[m
[32m+[m		[32mAirlineComparator ac = new AirlineComparator();[m
 		if( orderType == ORDERED_BY_AIRLINE) {[m
[31m-			[m
[32m+[m			[32mint low = 0;[m
[32m+[m			[32mint high = flights.length-1;[m
[32m+[m			[32mint mid = (low+high)/2;[m
[32m+[m			[32mwhile(low <= high && flight == null) {[m
[32m+[m				[32mif(ac.compare(key, flights[mid]) > 0) {[m
[32m+[m					[32mlow = mid+1;[m
[32m+[m				[32m}[m
[32m+[m				[32melse if(ac.compare(key, flights[mid]) < 0) {[m
[32m+[m					[32mhigh = mid-1;[m
[32m+[m				[32m}[m
[32m+[m				[32melse {[m
[32m+[m					[32mflight = flights[mid];[m
[32m+[m				[32m}[m
[32m+[m			[32m}[m
 		}[m
 		else {[m
 			for(int i = 0; i < flights.length && flight == null; i++) {[m
[31m-				if(flights[i].compareTo(key) == 0) {[m
[32m+[m				[32mif(ac.compare(key, flights[i]) == 0) {[m
 					flight = flights[i];[m
 				}[m
 			}[m
