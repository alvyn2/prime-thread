/*
Implement isPrime(number) - 3 points 

                    This function should take in a number and figure out if the value is prime or not. 

Implement countPrimes(min, max) - 3 points

                    This function should count the number of primes between the specified bounds min and max.

Implement sections of the main method - 4 points

                    You should correctly spawn the requisite number of threads and then wait for them to finish before printing out the answer.

2 points for proper conventions and clean code.

*/
        import java.time.Duration;
        import java.time.Instant;
        import java.util.Scanner;       
        public class PrimeThread{
            
            private static int total=0;
            //this provided thread calls functions you will need to run in order to calculate the number of threads between the provided min and max values.
            public static class CountPrimesThread extends Thread {
                int count = 0;
                int min, max;
                public CountPrimesThread(int min, int max) {
                    this.min = min;
                    this.max = max;
                }
                public void run() {
                    count = countPrimes(min,max);
                    System.out.println("There are " + count + 
                            " primes between " + min + " and " + max);
                    sendBack(count);
                    
                }
            }
            
            /**
             *Notice how this is a synchronized method! This allows multiple threads to access it safely.
             *We use this to keep track of the total number of primes found.
             */
            synchronized private static void sendBack(int count) {
                total += count;
            }
            /**
             * Count the primes between min and max, inclusive.
             * you need to implement this!
             */
            private static int countPrimes(int min, int max) {
                int current=max;
                int count=0;
                while(current>=min){
                    if(isPrime(current)){
                        //System.out.println(current +" is prime");
                        count++;
                    }
                    current--;
                }
                System.out.println("countPrimes finished");
                return count;
            }
        
            /**
             * Test whether x is a prime number.
             * x is assumed to be greater than 1.
             * You need to implement this!
             */
            private static boolean isPrime(int x) {
                if(x==1 || x==0){
                    return false;
                }
                for(int i=x-1;i>1;i--){
                    if(x%i==0){
                        return false;
                    }
                }

                return true;
            }
        
            public void test(){
                int num = 0;
                for(int i=0; i<=50; i++){  
                   num =num+i;
                   System.out.print(num+", ");
                }  
             }
            public static void main(String args[]) {  
                /* 
                int processors = Runtime.getRuntime().availableProcessors();
                if (processors == 1)
                    System.out.println("Your computer has only 1 available processor.\n");
                else
                    System.out.println("Your computer has " + processors + " available processors.\n");
                */
                
                
                    //uncomment the following once you're rady to take user input for the number of threads and max value to test
                Scanner reader = new Scanner(System.in);  // Reading from System.in
                System.out.println("Enter the number for hich you wish to know how many primes are smaller than it: ");
                int max = reader.nextInt();
                System.out.println("Enter the number of treads you wish to use in your program");
                //int threads=2;
               int threads = reader.nextInt();
                

               reader.close();
               // int max=1000;
                
                
               //Starting time
               Instant start = Instant.now();
               
               //comment the following line out once you've seen how this works.
               //new PrimeThread().test();
               
               
               //Write the code for spawning the desired number of CountPrimeThreads
               //be sure to divide the work among the specified number of threads efficiently. Use .join() to check to see if a thread is finished.
               int c=0;// c for current 
               CountPrimesThread[] threadsArr =new CountPrimesThread[threads];
               for(int i=0;i<threads;i++){
                    CountPrimesThread thread= new CountPrimesThread(c, (c+max/threads-i));
                    c+=max/threads+1;
                    threadsArr[i]=thread;
                }
                try{
                    for(CountPrimesThread t : threadsArr){
                        t.start();
                    }
                    
                    for(CountPrimesThread t : threadsArr){
                        t.join();
                    }

                }
                catch(Exception e){
                    System.out.println(e);
                }
                System.out.println("total: "+ total);
               //End time
               Instant end = Instant.now();
               long time = Duration.between(start, end).toMillis();
               System.out.println();
               System.out.println(time+" Milli seconds");
            
    }
}
