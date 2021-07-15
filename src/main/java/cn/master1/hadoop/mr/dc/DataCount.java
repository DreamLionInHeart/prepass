package cn.master1.hadoop.mr.dc;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DataCount {

    public static void main(String[] args) throws Exception{
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(DataCount.class);

        job.setMapperClass(DCMapper.class);
        /*当k2 v2 和k3 v3类型一一对应时，此行和下面一行可以省略。*/
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(DataBean.class);
        FileInputFormat.setInputPaths(job, new Path(args[0]));

        job.setReducerClass(DCReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setOutputValueClass(DataBean.class);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);

    }

    public static class DCMapper extends Mapper<LongWritable, Text, Text, DataBean>{

        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, DataBean>.Context context)
            throws IOException, InterruptedException{
            //接收数据

            String line = value.toString();
            String[] fileds = line.split("\t");
            String telNo = fileds[1];
            long up = Long.parseLong(fileds[8]);
            long down = Long.parseLong(fileds[9]);
            DataBean bean = new DataBean(telNo, up, down);
            context.write(new Text(telNo), bean);

        }
    }

    public static class DCReducer extends Reducer<Text, DataBean, Text, DataBean>{

        @Override
        protected void reduce(Text key, Iterable<DataBean> v2s, Context context)
            throws IOException, InterruptedException{

            long up_sum = 0;
            long down_sum = 0;
            for(DataBean bean : v2s){
                up_sum += bean.getUpPayLoad();
                down_sum += bean.getDownPayLoad();
            }
            DataBean bean = new DataBean("", up_sum, down_sum);
            context.write(key, bean);
        }
    }
}
