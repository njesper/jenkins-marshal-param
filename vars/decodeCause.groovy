import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

def call() {
  byte[] causeBz = currentBuild.getBuildCauses()[0].note.decodeBase64()
  byte[] data = new byte[causeBz.length * 4];
  ByteArrayInputStream bais = new ByteArrayInputStream(causeBz)
  int count;
  BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(bais);
  count = bzIn.read(data); // TODO: Add loop to extract all data for shure...
  bzIn.close();
  bais.close();
  def causeMap = [:]
  (new String(data, 0, count, "UTF-8").split("\n")).each {
      elem = it.split('=',2)
      causeMap[elem[0]]=elem[1]
  }
  return causeMap
}
