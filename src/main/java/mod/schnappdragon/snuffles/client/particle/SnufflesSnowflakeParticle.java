package mod.schnappdragon.snuffles.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SnowflakeParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class SnufflesSnowflakeParticle extends SnowflakeParticle {
    protected SnufflesSnowflakeParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);

        this.quadSize = 0.05F * (this.random.nextFloat() * this.random.nextFloat() + 1.0F);
        this.lifetime = (int) (40.0D / ((double) this.random.nextFloat() * 0.8D + 0.2D)) + 5;
        this.setColor(0.923F, 0.964F, 0.999F);
        this.gravity = 0.08F;
        this.yd = ySpeed;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet p_172304_) {
            this.sprites = p_172304_;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SnufflesSnowflakeParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
        }
    }
}
