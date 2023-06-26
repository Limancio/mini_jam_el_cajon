package maths;

public class mat4 {
	public float data[];
	
	public mat4() {
		data = new float[16];
	}

	public void 
	imat() {
		data[0]  = 1f;
		data[5]  = 1f;
	    data[10] = 1f;
	    data[15] = 1f;
	}
	
	public void 
	ortho(float w, float h, float zn, float zf) {
		float frange = 1.0f / (zf - zn);
	    
		data[0] = 2.0f / w;
		data[1] = 0.0f; 
		data[2] = 0.0f; 
		data[3] = 0.0f; 
	  
		data[4] = 0.0f; 
		data[5] = 2.0f / h;
	    data[6] = 0.0f; 
	    data[7] = 0.0f; 
	  
	    data[8] = 0.0f; 
	    data[9] = 0.0f;
	    data[10] = frange;
	    data[11] = 0.0f; 
	  
	    data[12] = 0.0f;
	    data[13] = 0.0f; 
	    data[14] = -frange * zn; 
	    data[15] = 1.0f;
	}
}
