package pugz.omni.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import pugz.omni.common.entity.paradise.SeahorseEntity;

public class SeahorseModel<E extends SeahorseEntity> extends EntityModel<E> {
	private final ModelRenderer body;
	private final ModelRenderer tail;
	private final ModelRenderer leftFin;
	private final ModelRenderer rightFin;
	private final ModelRenderer head;

	public SeahorseModel() {
		textureWidth = 32;
		textureHeight = 32;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(body, 0.2618F, 0.0F, 0.0F);
		body.setTextureOffset(0, 8).addBox(-1.0F, -9.8637F, 1.0353F, 2.0F, 6.0F, 3.0F, 0.0F, false);
		body.setTextureOffset(0, 14).addBox(0.0F, -14.5F, 3.25F, 0.0F, 11.0F, 3.0F, 0.0F, false);

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, -4.0F, 4.0F);
		body.addChild(tail);
		tail.setTextureOffset(10, 10).addBox(-0.5F, 0.0F, -4.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);

		leftFin = new ModelRenderer(this);
		leftFin.setRotationPoint(1.0F, -8.0F, 2.5F);
		body.addChild(leftFin);
		setRotationAngle(leftFin, 0.0F, 0.0F, -0.3927F);
		leftFin.setTextureOffset(5, 6).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);

		rightFin = new ModelRenderer(this);
		rightFin.setRotationPoint(-1.0F, -8.0F, 2.5F);
		body.addChild(rightFin);
		setRotationAngle(rightFin, 0.0F, 0.0F, 0.3927F);
		rightFin.setTextureOffset(5, 6).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, true);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 14.0F, 1.0F);
		head.setTextureOffset(0, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
		head.setTextureOffset(12, 4).addBox(-0.5F, -2.5F, -8.0F, 1.0F, 2.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(SeahorseEntity seahorse, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		float f = ((float)(seahorse.getEntityId() * 3) + ageInTicks) * 0.13F;
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.leftFin.rotateAngleZ = MathHelper.cos(f) * 16.0F * ((float)Math.PI / 240F);
		this.rightFin.rotateAngleZ = -this.leftFin.rotateAngleZ;
		this.tail.rotateAngleX = -(5.0F + MathHelper.cos(f * 2.0F) * 5.0F) * ((float)Math.PI / 180F);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		head.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}