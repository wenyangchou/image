package com.fooww.research

import com.fooww.research.image.DHash
import org.apache.spark.rdd.RDD

/**
  * @author ：zwy
  * @date ：Created in 2019/8/26 15:10
  */
object DHashOperate {

  /**
    *
    * @param data (主键，图片路径)
    * @return (主键，hash值)
    */
  def getDHash(data:RDD[(Long,String)]):RDD[(Long,String)]={
    data.map(f=>{
      (f._1,DHash.getImageDHash(f._2))
    })
  }
}
