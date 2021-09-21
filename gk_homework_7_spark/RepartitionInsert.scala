package task3.rules

import org.apache.spark.internal.Logging
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.catalog.HiveTableRelation
import org.apache.spark.sql.catalyst.rules.Rule
import org.apache.spark.sql.hive.execution.InsertIntoHiveTable
import org.apache.spark.sql.catalyst.plans.logical.{LogicalPlan, Repartition}

case class RepartitionInsert(spark: SparkSession) extends Rule[LogicalPlan] with Logging {

  override def apply(plan: LogicalPlan): LogicalPlan = {
    plan transformDown {
      case i @ InsertIntoHiveTable(table, partition, plan @ HiveTableRelation(_, _, _, _, _), overwrite, ifPartitionNotExists, outputColumnNames) =>
        i.copy(table, partition, Repartition(1, shuffle = true, plan), overwrite, ifPartitionNotExists, outputColumnNames)
    }
  }
}