package stock.test.spider.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * <p>
 *
 * </p>
 *
 * @author night
 * @since 2020-06-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="StockCode对象", description="")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class StockIndustry implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @ApiModelProperty(value = "股票名字")
  private String name;



  @ApiModelProperty(value = "股票代码")
  private String code;

  @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
  @Column(name="is_deleted")
  private Boolean deleted;

  @ApiModelProperty(value = "创建时间")
  @CreatedDate
  private Date gmtCreate;

  @ApiModelProperty(value = "更新时间")
  @LastModifiedDate
  private Date gmtModified;

}
